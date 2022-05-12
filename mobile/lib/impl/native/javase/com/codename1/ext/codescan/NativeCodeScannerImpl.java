package com.codename1.ext.codescan;

public class NativeCodeScannerImpl implements com.codename1.ext.codescan.NativeCodeScanner{
    private static boolean cameraUsageDescriptionChecked;
    
        
    
    private static void checkCameraUsageDescription() {
        if (!cameraUsageDescriptionChecked) {
            cameraUsageDescriptionChecked = true;
            
            java.util.Map<String, String> m = com.codename1.ui.Display.getInstance().getProjectBuildHints();
            if(m != null) {
                if(!m.containsKey("ios.NSCameraUsageDescription")) {
                    com.codename1.ui.Display.getInstance().setProjectBuildHint("ios.NSCameraUsageDescription", "Some functionality of the application requires your camera");
                }
            }
        }
    }
    
    
    public void scanQRCode() {
    }

    public void scanBarCode() {
    }

    public boolean isSupported() {
        checkCameraUsageDescription();
        return false;
    }

}
