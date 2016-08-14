#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^E:/eclipse/amplicaciones_user/Cygwin/lib!/usr/lib!ig;s! E:/eclipse/amplicaciones_user/Cygwin/lib! /usr/lib!ig;s!^E:/eclipse/amplicaciones_user/Cygwin/bin!/usr/bin!ig;s! E:/eclipse/amplicaciones_user/Cygwin/bin! /usr/bin!ig;s!^E:/eclipse/amplicaciones_user/Cygwin/!/!ig;s! E:/eclipse/amplicaciones_user/Cygwin/! /!ig;s!^G:!/cygdrive/g!ig;s! G:! /cygdrive/g!ig;s!^F:!/cygdrive/f!ig;s! F:! /cygdrive/f!ig;s!^E:!/cygdrive/e!ig;s! E:! /cygdrive/e!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi
