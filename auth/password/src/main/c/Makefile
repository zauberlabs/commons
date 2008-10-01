
all:
	$(MAKE) -C libdaemon -f Makefile.mine
	$(MAKE) -C libmisc -f Makefile.mine
	$(MAKE) -C libcrash -f Makefile.mine
	$(MAKE) -C src 

clean:
	$(MAKE) -C libdaemon -f Makefile.mine clean
	$(MAKE) -C libmisc -f Makefile.mine   clean
	$(MAKE) -C src clean
