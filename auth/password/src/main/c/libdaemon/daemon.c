#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <signal.h>
#include <pwd.h>
#include <grp.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>

#include <libmisc/trace.h>
#include <libmisc/i18n.h>


int
switch_to(const char *user, const char *group)
{	int ret = 0;
	struct passwd *p;
	struct group *g;
	
	if(  group )
	{	g = getgrnam(group);
		if( g )
			ret = setregid(g->gr_gid, g->gr_gid);
		else
			ret = -1;
	}
	
	
	if( ret != -1 && user )
	{	p = getpwnam(user);
		if( p )
			ret = setreuid(p->pw_uid, p->pw_uid);
		else
			ret = -1;
	}
	
	return ret;
}


extern const char * progname;
#ifdef HAVE_SYSLOG_H
 #include <syslog.h>
#endif

void
open_syslogd(void)
{
	#ifdef HAVE_SYSLOG_H
		 openlog(progname, LOG_PID, LOG_DAEMON);
	#endif
}

void
close_syslogd(void)
{
	#ifdef HAVE_SYSLOG_H
		 closelog();
	 #endif
}

int
hechizar(void)
{	unsigned i;
	int pid;
	int sid;
	
 	for (i=0;i<3;i++) {
		close(i);
		open("/dev/null", O_RDWR);
	}

	open_syslogd();
	rs_trace_to(rs_trace_syslog);
	
	/* ignore sighup */
	signal(SIGHUP, SIG_IGN);
	
	pid =  fork();
	if( pid  == -1 ) {
		rs_log_error(_("forking: %s"), strerror(errno));
		return -1;
	}
	else if( pid == 0 )
		; /* child */
	else
		exit(0);

	/* new session */
	if((sid = setsid()) <0 )
	{       rs_log_error("setsid: %s\n",strerror(errno));
		return -1;
	}
	
	umask(0022);
	chdir("/");

	return 0;
}

#ifdef TEST_DRIVER_SWITCHTO
#include <stdio.h>
int
main(int argc, char **argv)
{	int ret;

	if( argc == 3 )
	{	printf("switching to %s %s -> %d\n",argv[1], argv[2],
			(ret = switch_to(argv[1], argv[2])));

		if( ret == 0 ) {
			sync();
			sleep(20);
		}
		
	}
	return 0;
}
#endif
