# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX M4 core Demo images"
SECTION = "app"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6dfb32a488e5fd6bae52fbf6c7ebb086"

inherit deploy fsl-eula-unpack2

SOC ?= "imx8qm"
SOC_mx7ulp= "imx7ulp"
SOC_mx8mm= "imx8mm"
SOC_mx8mq= "imx8mq"
SOC_mx8qm= "imx8qm"
SOC_mx8qxp= "imx8qx"

IMX_PACKAGE_NAME = "${SOC}-m4-demo-${PV}"
SRC_URI_NAME = "${SOC}"

SRC_URI[imx7ulp.md5sum] = "bcc2dd48a28294b6dea42cbb2ad84ac5"
SRC_URI[imx7ulp.sha256sum] = "2958a3710bcbaa86be7e8cfa87f3db681cb5a3ddb24efb486bfe81dd105c99be"

SRC_URI[imx8mm.md5sum] = "cc9781bca30fed966937ad9e7a57c5d6"
SRC_URI[imx8mm.sha256sum] = "4e41e270506870e0fd2d53e3d69afdcf6d613d099b0da5c516a512944c5c12c3"

SCR = "SCR-${SOC}-m4-demo.txt"

do_deploy () {
   # Install the demo binaries
   cp ${D}/* ${DEPLOYDIR}/
}

addtask deploy before do_build after do_install

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx7ulp|mx8mm)"
