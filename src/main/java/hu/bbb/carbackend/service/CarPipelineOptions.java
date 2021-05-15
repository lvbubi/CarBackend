package hu.bbb.carbackend.service;

import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

public interface CarPipelineOptions extends GcpOptions {
    @Validation.Required
    @Description("Bucket of input images")
    String getBucket();

    void setBucket(String value);

    @Validation.Required
    @Description(("Base url of Google Cloud functions"))
    String getFunctionBase();

    void setFunctionBase(String value);
}
