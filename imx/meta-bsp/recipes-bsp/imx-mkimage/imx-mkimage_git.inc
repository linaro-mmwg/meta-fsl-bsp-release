# Copyright 2017-2018 NXP

IMX_MKIMAGE_SRC ?= "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https"
SRCBRANCH = "imx_4.14.98_2.1.0"
SRC_URI = "${IMX_MKIMAGE_SRC};branch=${SRCBRANCH}"
SRCREV = "02b74df28cc13de9df4df3a1c88983bf0a0430fc"

DEPENDS = "zlib-native openssl-native"
