/* A standalone XML-RPC server written for checking password strength. */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <errno.h>

#include <unistd.h>

#include <sys/types.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#include <xmlrpc-c/base.h>
#include <xmlrpc-c/server.h>
#include <xmlrpc-c/server_abyss.h>

#include <crack.h>

#include <libdaemon/daemon.h>
#include <libmisc/newopt.h>
#include <libmisc/trace.h>

#define ATTR_UNUSED __attribute__((__unused__))
#define VERSION 0.0


const char * progname;
const char * rs_program_name;

static xmlrpc_value *
sample_add(xmlrpc_env *   const env, 
           xmlrpc_value * const param_array, 
           void *         const user_data ATTR_UNUSED) {
    char* const OK_MESSAGE = "OK";
    char* pass[1];
    char const* msg;

    /* Parse our argument array. */
    xmlrpc_decompose_value(env, param_array, "(s)", pass);
    if (env->fault_occurred) {
        return NULL;
    }
    
    msg = FascistCheck( pass[0], "/tmp/foo/");

    if (msg != NULL) {
	/*Check With errors*/
        /* Return our result. */
        return xmlrpc_build_value(env, "s", msg);
    } else {
	/* Check ok*/
        return xmlrpc_build_value(env, "s", OK_MESSAGE);
    }
}

struct options {
    char *listen_addr;
    char *username;
    char *groupname;
    int port;
    char *logfile;
    int fork;
};


static void
usage(void) {
    printf("%s [-hVf] [--help] [--version] [--listen <address>] [--port port] [--log logfile]\n",
    progname);

    exit( EXIT_SUCCESS );
}

static void
version(void)
{
    printf( "%s %s\n"
        "\n"
        "This is free software:\n"
        " There is NO warranty; not even for MERCHANTABILITY or\n"
        " FITNESS FOR A PARTICULAR PURPOSE\n",progname,VERSION);

    exit( EXIT_SUCCESS );
}


static void
help ( void )
{   printf (
    "Usage: %s [OPTION]\n\n",progname);
    printf (
    "OPTIONS\n"
    /* X   X                      X */
    " -V   --version              print the version info and dies\n"
    " -h   --help                 prints this message\n"
    "      --listen <address>     listen to an specific interface\n"
    "      --user   <username>    run server as user <username>\n"
    "      --group  <groupname>   run server as group <groupname>\n"
    "      --port  <port>         run server in port [default: 9097]\n"
    "      --log  <filename>      logfile\n"
    " -f   --fork                 fork (modo servidor)\n"
    "\n"
    "Send bugs to http://tracker.zauber.com.ar/\n"
    "\n");

    exit(EXIT_SUCCESS);
}


static int
parseOptions(int argc, char * const * argv, struct options *opt)
{   int i, ret = 0;
    static optionT lopt[]=
    {/*00*/ {"help",    OPT_NORMAL, 0,  OPT_T_FUNCT, (void *) help },
     /*01*/ {"h",       OPT_NORMAL, 1,  OPT_T_FUNCT, (void *) help },
     /*02*/ {"version", OPT_NORMAL, 0,  OPT_T_FUNCT, (void *) version},
     /*03*/ {"V",       OPT_NORMAL, 1,  OPT_T_FUNCT, (void *) version},
     /*04*/ {"listen",  OPT_NORMAL, 0,  OPT_T_GENER, NULL },
     /*05*/ {"user",    OPT_NORMAL, 0,  OPT_T_GENER, NULL },
     /*06*/ {"group",   OPT_NORMAL, 0,  OPT_T_GENER, NULL },
     /*07*/ {"port",    OPT_NORMAL, 0,  OPT_T_INT,   NULL },
     /*08*/ {"log",     OPT_NORMAL, 0,  OPT_T_GENER, NULL },
     /*09*/ {"f",       OPT_NORMAL, 1,  OPT_T_FLAG,  NULL },
     /*10*/ {"fork",    OPT_NORMAL, 0,  OPT_T_FLAG,  NULL },
        {NULL}
    };  
    lopt[4].data = &(opt->listen_addr);
    lopt[5].data = &(opt->username);
    lopt[6].data = &(opt->groupname);
    lopt[7].data = &(opt->port);
    lopt[8].data = &(opt->logfile);
    lopt[9].data = lopt[10].data = &(opt->fork);

    i = GetOptions(argv, lopt, 0, 0);
    if( i < 0 ) {
        rs_log_error("parsing options");
        ret = -1;
    } else if( argc - i < 0 ) { 
        usage();
        ret = -1;
    }
    return ret;
}

static int
pop3filter_socket_listen(const char *listen_addr, short port) { 
    struct sockaddr_in servAddr;
    int sd = -1;

    /* create socket */
    sd = socket(AF_INET, SOCK_STREAM, 0);
    if(sd < 0) { 
        rs_log_error("creating socket: %s",strerror(errno));
        sd = -1;
    } else {
        servAddr.sin_family = AF_INET;
        servAddr.sin_port = htons(port);
        if( listen_addr ) {
            if (!inet_aton(listen_addr, &servAddr.sin_addr)) {
                rs_log_error("invalid IPv4 address: %s",
                         listen_addr);
                close(sd);
                sd = -1;
            }
         } else {
            servAddr.sin_addr.s_addr = htonl(INADDR_ANY);
         }
         
         if( sd == -1 ) {
            /* void */
         } else if(bind(sd, (struct sockaddr *) &servAddr,
                   sizeof(servAddr)) == -1 ) {
            if( listen_addr ) {
                rs_log_error("binding socket (%s): %s",
                           listen_addr, strerror(errno));
            } else {
                rs_log_error("binding socket: %s",
                           strerror(errno));
            }
            close(sd);
            sd = -1;
        } else if(listen(sd,5) == -1) { 
            rs_log_error("listening socket: %s",strerror(errno));
            close(sd);
            return -1;
        }
    }
    return sd;
}

int 
main(int           const argc, 
    char * const *       argv) {
    xmlrpc_server_abyss_parms serverparm;
    xmlrpc_registry * registryP;
    xmlrpc_env env;

    struct options opt;
    int ret = EXIT_SUCCESS;

    rs_program_name = progname = argv[0];

    /* defaults */
    memset(&opt, 0, sizeof(opt));
    opt.port = 9097;

    if(parseOptions(argc, argv, &opt ) < 0) {
        ret = EXIT_FAILURE;
    } else if((opt.username || opt.groupname) 
              && switch_to(opt.username, opt.groupname) == -1 ) {
        rs_log_error("switching to username");
        ret = EXIT_FAILURE;
    } else {
        int sd = -1;
        if(opt.fork) {
            rs_trace_to(rs_trace_syslog);
        }
        if(opt.listen_addr) {
            if((sd = pop3filter_socket_listen(opt.listen_addr, opt.port)) < 0 ) {
                ret = EXIT_FAILURE;
            }
        }
        if(ret != EXIT_FAILURE) {
            xmlrpc_env_init(&env);

            registryP = xmlrpc_registry_new(&env);

            xmlrpc_registry_add_method(
                &env, registryP, NULL, "password.check", &sample_add, NULL);

            serverparm.config_file_name = NULL;
            serverparm.registryP        = registryP;
            if(opt.logfile) {
                serverparm.log_file_name    = opt.logfile;
            }

            serverparm.log_file_name  = "/tmp/xmlrpc_log";
            if(opt.listen_addr) {
                serverparm.keepalive_timeout  = 0;
                serverparm.keepalive_max_conn = 0;
                serverparm.timeout            = 0;
                serverparm.socket_handle = sd;
                serverparm.socket_bound = TRUE;
            } else {
                serverparm.port_number = opt.port;
            }

            if(opt.fork) {
                hechizar();
            }

            rs_log_info("Running password validator server...,"
                        " running on %s port %d",
                     opt.listen_addr ? opt.listen_addr : "0.0.0.0",
                     opt.port);

            xmlrpc_server_abyss(&env, &serverparm, 
                               XMLRPC_APSIZE(log_file_name));
        }
    }

    return ret;
}
