
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://0001-Revert-aplay-fix-pcm_read-return-value.patch"

COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|mx6ul)"
