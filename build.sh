# path to the ADT tool in Flash Builder sdks
ADT="/Applications/Adobe Flash Builder 4.7/sdks/4.6.0/bin/adt"

# native project folder
NATIVE_FOLDER=jar

# AS lib folder
LIB_FOLDER=aslib

# app folder
APP_PROJECT=app

# name of ANE file
ANE_NAME=ane/JAIRBridge.ane

SWC_NAME=JAIRBridgeLibrary.swc

# JAR filename
JAR_NAME=JAIRBridge.jar

#===================================================================

echo "****** preparing ANE package sources *******"

rm ${ANE_NAME}
rm -rf ./build/ane
mkdir -p ./ane
mkdir -p ./build/ane
mkdir -p ./build/ane/Android-ARM

# copy resources
cp -R ./${NATIVE_FOLDER}/res/ ./build/ane/Android-ARM/res

# create the JAR file
# jar cf ./build/ane/Android-ARM/${JAR_NAME} -C ./${NATIVE_FOLDER}/bin .

# grab the extension descriptor and SWC library 
cp ./${LIB_FOLDER}/src/extension.xml ./build/ane/
cp ./${LIB_FOLDER}/bin/${SWC_NAME} ./build/ane/
unzip ./build/ane/${SWC_NAME} -d ./build/ane
cp ./build/ane/library.swf ./build/ane/Android-ARM
cp ./${NATIVE_FOLDER}/bin/${JAR_NAME} ./build/ane/Android-ARM

echo "****** creating ANE package *******"

"$ADT" 	-package -storetype PKCS12 -keystore ./p12.p12 -storepass soad7623 -tsa none -target ane ${ANE_NAME} ./build/ane/extension.xml -swc ./build/ane/${SWC_NAME}\
 	-platform Android-ARM -C ./build/ane/Android-ARM/ .

echo "****** ANE package created*******"