* Dependencias de xmlrpc:
Abyss http://abyss.sourceforge.net/ (los sources vienen con cracklib) 

* Compilacion de xmlrpc_cracklib_check.c:

gcc -c -I/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/lib/abyss/src 
-DNDEBUG -Wall -Wundef -Wimplicit -W -Winline -Wundef -Wmissing-declarations -Wstrict-prototypes -Wmissing-prototypes -fno-common -g -O3    
xmlrpc_cracklib_check.c

gcc -o xmlrpc_cracklib_check  -lcrack xmlrpc_cracklib_check.o  
/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/src/.libs/libxmlrpc_server_abyss.a 
/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/src/.libs/libxmlrpc_server.a 
/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/lib/abyss/src/.libs/libxmlrpc_abyss.a  
-lpthread /home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/src/.libs/libxmlrpc.a 
/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/lib/libutil/.libs/libxmlrpc_util.a 
/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/lib/expat/xmlparse/.libs/libxmlrpc_xmlparse.a 
/home/christian/Desktop/frameworks/xmlrpc-c-1.06.16/lib/expat/xmltok/.libs/libxmlrpc_xmltok.a

* Ejecucion ./mlrpc_cracklib_check <puerto>

* Se incluye en resources el archivo words.spanish.latin1 es de la distribucion
wspanish de debian. Para instalarlo copiarlo a alguno de los siguientes dirs:
 -/usr/share/dict
 -/usr/dict 
 -/usr/local/share/dict 
 -/usr/local/dict  
De la misma manera se pueden agregar todos los diccionarios que se quiera.
Se puede forzar el reconocimiento ejecutando: 
 -/etc/cron.daily/cracklib-runtime
 De otro modo, el cron de cracklib lo reconocera cuando se ejcute.
 
 * Configurar el parametro de host y method name de CracklibPasswordValidator:
 el methodname es password.check y el host es http://localhost:<puerto> por 
 default el puerto esta configurado en 9097.

