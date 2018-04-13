SUMMARY = "TI RTOS driver for Power Management module (PM)"

inherit ti-pdk

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://Power.h;beginline=1;endline=31;md5=527b91fdcd26cd19ac07a754f45dedbe"

COMPATIBLE_MACHINE = "ti33x|ti43x|omap-a15|k2g"
PACKAGE_ARCH = "${MACHINE_ARCH}"


PM_LLD_GIT_URI = "git://git.ti.com/keystone-rtos/pm-lld.git"
PM_LLD_GIT_PROTOCOL = "git"
PM_LLD_GIT_BRANCH = "int_pm_am335x"
PM_LLD_GIT_BRANCH_dra7xx-evm = "master"
PM_LLD_GIT_BRANCH_am57xx-evm = "master"
PM_LLD_GIT_DESTSUFFIX = "git/ti/drv/pm"

# Below commit ID corresponds to "DEV.PM_LLD.01.04.00.04"
PM_LLD_SRCREV = "1c0cae9b001137f68f34bf9168d7c3fa9036b1a6"

# Below commit ID corresponds to "DEV.PM_LLD.01.08.00.17"
PM_LLD_SRCREV_dra7xx-evm = "720f7df19f1b34cd72d8d3937baf5ff0011fd5dd"
PM_LLD_SRCREV_am57xx-evm = "720f7df19f1b34cd72d8d3937baf5ff0011fd5dd"


BRANCH = "${PM_LLD_GIT_BRANCH}"
SRC_URI = "${PM_LLD_GIT_URI};destsuffix=${PM_LLD_GIT_DESTSUFFIX};protocol=${PM_LLD_GIT_PROTOCOL};branch=${BRANCH}"

SRCREV = "${PM_LLD_SRCREV}"
PV = "01.04.00.04"
PR = "r0"

S = "${WORKDIR}/${PM_LLD_GIT_DESTSUFFIX}"

DEPENDS_append = " ti-sysbios \
            osal-rtos \
"
DEPENDS_append_ti33x = " starterware-rtos \
"
DEPENDS_append_ti43x = " starterware-rtos \
"
export PDK_PM_ROOT_PATH = "${WORKDIR}/build"
export DEST_ROOT="${S}"

# Build with make instead of XDC
TI_PDK_XDCMAKE = "0"

do_compile_append() {
    # Delete archive created by XDC release command since it does not contain all content
    find -name "*.tar" -exec rm -f {} \;

    # Archive from build/ to capture ti/drv/pm/ in archive
    cd ${B}
    tar -cf pm_lld.tar --exclude='*.tar' ./*
}

INSANE_SKIP_${PN} = "arch staticdev"
