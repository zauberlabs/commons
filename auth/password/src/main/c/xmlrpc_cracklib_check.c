/* A standalone XML-RPC server written for checking password strength. */

#include <stdlib.h>
#include <stdio.h>
#ifndef WIN32
#include <unistd.h>
#endif

#include <xmlrpc-c/base.h>
#include <xmlrpc-c/server.h>
#include <xmlrpc-c/server_abyss.h>

#include <crack.h>
#include "config.h"  /* information about this build environment */

static xmlrpc_value *
sample_add(xmlrpc_env *   const env, 
           xmlrpc_value * const param_array, 
           void *         const user_data ATTR_UNUSED) {
    char* const OK_MESSAGE = "OK";
    char* pass[1];
    /* Parse our argument array. */
    xmlrpc_decompose_value(env, param_array, "(s)", pass);
    if (env->fault_occurred) {
        return NULL;
    }
    char const* msg;
    msg = FascistCheck( pass[0], CRACKLIB_DICTPATH );

    if (msg != NULL) {
	/*Check With errors*/
        /* Return our result. */
        return xmlrpc_build_value(env, "s", msg);
    } else {
	/* Check ok*/
        return xmlrpc_build_value(env, "s", OK_MESSAGE);
    }
}



int 
main(int           const argc, 
     const char ** const argv) {

    xmlrpc_server_abyss_parms serverparm;
    xmlrpc_registry * registryP;
    xmlrpc_env env;

    if (argc-1 != 2) {
        fprintf(stderr, "You must specify 2 argument:  The TCP port "
                "number on which the server will accept connections "
                "for RPCs and the log file path.  You specified %d arguments.\n",  argc-1);
        exit(1);
    }
    
    xmlrpc_env_init(&env);

    registryP = xmlrpc_registry_new(&env);

    xmlrpc_registry_add_method(
        &env, registryP, NULL, "password.check", &sample_add, NULL);

    serverparm.config_file_name = NULL;
    serverparm.registryP        = registryP;
    serverparm.port_number      = atoi(argv[1]);
    serverparm.log_file_name    = argv[2];

    printf("Running XML-RPC server...\n");

    xmlrpc_server_abyss(&env, &serverparm, XMLRPC_APSIZE(log_file_name));

    /* xmlrpc_server_abyss() never returns */

    return 0;
}
