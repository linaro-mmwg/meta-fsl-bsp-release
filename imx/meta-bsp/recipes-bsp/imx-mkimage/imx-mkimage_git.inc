# Copyright 2017-2018 NXP

IMX_MKIMAGE_SRC ?= "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https"
SRCBRANCH = "imx_4.14.98_2.1.0"
SRC_URI = "${IMX_MKIMAGE_SRC};branch=${SRCBRANCH}"
SRCREV = "abd6ce551a7b81fc6953d32d92b24a4a1d4b214e"

DEPENDS = "zlib-native openssl-native"
