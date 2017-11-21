package com.virtusa.weatherapp.setup;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

/**
 * We can make use of such kind of class in case we need handle the manifest
 */

public class WARobolectricTestRunner extends RobolectricGradleTestRunner {

    public WARobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        return new AndroidManifest(Fs.fileFromPath("../src/main/AndroidManifest.xml"),
                Fs.fileFromPath("../src/main/res"),
                Fs.fileFromPath("../src/main/assets"));
    }


    @Override
    protected org.robolectric.internal.bytecode.ShadowMap createShadowMap() {
        return super.createShadowMap().newBuilder()
                .build();
    }
}
