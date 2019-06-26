# Copyright 2017-2018 NXP

IMX_MKIMAGE_SRC ?= "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https"
SRCBRANCH = "imx_4.14.98_2.1.0"
SRC_URI = "${IMX_MKIMAGE_SRC};branch=${SRCBRANCH}"
SRCREV = "8cbdcc175b1e1fd8670b7c479687b569f0352650"

DEPENDS = "zlib-native openssl-native"
