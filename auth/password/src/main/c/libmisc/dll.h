
#ifdef WIN32
 #ifdef EXPORT_LIBMISC
  #define EXPORT __declspec(dllexport)
 #else
  #define EXPORT /*__declspec(dllimport) */
 #endif
#else
 #define EXPORT 
#endif
