/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.xpack.ml.featureindexbuilder;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.license.XPackLicenseState;
import org.elasticsearch.xpack.core.XPackFeatureSet;
import org.elasticsearch.xpack.core.XPackField;
import java.util.Map;

public class FeatureIndexBuilderFeatureSet implements XPackFeatureSet {

    private final boolean enabled;
    private final XPackLicenseState licenseState;

    @Inject
    public FeatureIndexBuilderFeatureSet(Settings settings, @Nullable XPackLicenseState licenseState) {
        this.enabled = true; // XPackSettings.FEATURE_INDEX_BUILDER_ENABLED.get(settings);
        this.licenseState = licenseState;
    }

    @Override
    public String name() {
        return XPackField.FIB;
    }

    @Override
    public String description() {
        return "Time series feature index creation";
    }

    @Override
    public boolean available() {
        return licenseState != null && licenseState.isMachineLearningAllowed(); // todo: part of ML?
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public Map<String, Object> nativeCodeInfo() {
        return null;
    }

    @Override
    public void usage(ActionListener<XPackFeatureSet.Usage> listener) {
        // TODO expose the currently running rollup tasks on this node? Unclear the best
        // way to do that
        listener.onResponse(new FeatureIndexBuilderFeatureSetUsage(available(), enabled()));
    }
}
