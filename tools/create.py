#===================================================================================================
# Pender Create Script
#
#
# Lorin Beer
# lorin@adobe.com
#===================================================================================================

import os
import sys
import shutil
import urllib2
from optparse import OptionParser

#===================================================================================================

source_dirs = ["src","assets","bin"]

liburl = "https://github.com/downloads/doggerelverse/Pender-android/rhino1_7R2.jar"

str_antlib = "jar.libs.dir=libs"

#===================================================================================================

STRING_BASE_ERROR = "Pender Create Script Error: "

#===================================================================================================
#android shell commands
droidcreatecmd = { "base" : "android create project",
                   "--target"   : "android-14",
                   "--name"     : "PenderDemo",
                   "--path"     : "Pender",
                   "--activity" : "PenderActivity",
                   "--package"  : "com.pender" }

droidupdatecmd = { "base" : "android update project",
                   "--path"    : "Pender",
                   "--library" : "libs" }

#===================================================================================================
#definition of command line option parser
parser = OptionParser()

parser.add_option("-t", "--target", 
                  help="Android platform library target [default] : 'android-14'")

parser.add_option("-n", "--name",
                  help="Project name [default] : 'PenderDemo'")

parser.add_option("-p", "--path",
                  help="new Pender project directory [default] : 'Pender' (relative path)")

parser.add_option("-k", "--package",
                  help="Android package name for the app [default] : 'com.pender'")

parser.add_option("-a", "--activity",
                  help="new Pender project directory [default] : 'PenderActivity'")

#===================================================================================================

def execcmd(args):
    ""
    print "Executing " + droidcreatecmd['base']
    cmd = args["base"]
    for key,val in args.items():
        if key != "base":
            cmd = cmd + " " + key + " " + val
    cmd = cmd + " " + "> android_out.txt"
    os.system(cmd)

#===================================================================================================

def replacesource(dest,dirs):
    "replace source directories in generated project with Pender source"
    print "Updating project source"
    #grab the execution directory of this script
    projectroot = os.getcwd().split("/")
    #calculate the project root
    projectroot = "/".join(projectroot[0:len(projectroot)-1])
    #list all directories in project root and dest
    rootcontent = os.listdir(projectroot)
    destcontent = os.listdir(dest)
    #recursively copy each directory specified, provided it exists
    for dir in dirs:
        if dir not in rootcontent:
            print STRING_BASE_ERROR + "directory " + dir + " not found in project directory. Pender may not run correctly."
        else:
            #if present, remove from generated project directory
            if dir in destcontent: shutil.rmtree( dest + "/" + dir )
            d = dest + "/" + dir
            s = projectroot + "/" + dir
            shutil.copytree(s,d)


#===================================================================================================

def grablib(url,localpath):
    "download the file at url and store it at localpath"
    sysout("Grabbing Rhino JAR from " + liburl + " ...")
    request = urllib2.Request(url)
    try:
        f = urllib2.urlopen(request)
        local = open(localpath, "w")
        local.write(f.read())
        local.close()
        print("Great Success!")
    except urllib2.HTTPError, e:
        print "HTTP Error:",e.code , url
    except urllib2.URLError, e:
        print "URL Error:",e.reason , url

#===================================================================================================
def updateproperties():
    "update ant build properties"
    sysout("Updating project build properties ...")
    antpath = droidcreatecmd['--path']+"/ant.properties"
    try:
        f = open(antpath,'a')
        #update ant.properties with external libs
        f.write(str_antlib)
        #we're done with f
        f.close()
        print("Great Success!")
    except IOError as e:
        print "IO Error:",e.code,antpath

#===================================================================================================

def create():
    ""
    #create the android project
    execcmd(droidcreatecmd)
    #update source
    replacesource(droidcreatecmd['--path'],source_dirs)
    #download compiled lib
    libpath = "libs/rhino1_7R2.jar"
    grablib(liburl, droidcreatecmd['--path'] + "/" + libpath)
    #update ant.properties with lib references
    updateproperties()

#===================================================================================================

def sysout(msg):
    ""
    sys.stdout.write(msg)
    sys.stdout.flush()

#===================================================================================================

if __name__ == "__main__":
    (options, args) = parser.parse_args()
    options = vars(options)
    for opt in options.keys():
        if options[opt]:
            arg = options[opt]
            droidcreatecmd['--'+opt] = arg 
    create()
    print "Everything seems to have gone better than expected, try 'ant debug' in your project directory"

#===================================================================================================
