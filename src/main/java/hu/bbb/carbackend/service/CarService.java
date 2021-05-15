package hu.bbb.carbackend.service;

import hu.bbb.carbackend.service.CarPipelineOptions;
import hu.bbb.carbackend.service.CloudFunctionFn;
import hu.bbb.carbackend.service.ListObjects;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.jobrunr.jobs.annotations.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CarService {
    private static final DoFn<String, String> VISION_FN = new CloudFunctionFn("car_vision", "image_uri");
    private static final DoFn<String, String> SCRAPE_FN = new CloudFunctionFn("car_scraper", "car_type");


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Job(name = "The sample job without variable")
    public void execute() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error("Error while executing sample job", e);
        } finally {
            logger.info("Sample job has finished...");
        }
    }

    public void executeCarDataflow() {
        CarPipelineOptions options =
                PipelineOptionsFactory.fromArgs("--project=car-dataflow",
                        "--bucket=car-dataflow",
                        "--functionBase=https://europe-west3-car-dataflow.cloudfunctions.net").withValidation().as(CarPipelineOptions.class);
        runCarDataflow(options);
    }

    static void runCarDataflow(CarPipelineOptions options) {
        Pipeline p = Pipeline.create(options);
        p.apply("Read Images", Create.of(ListObjects.getImagePaths(options.getProject(), options.getBucket())))
                .apply("debug", MapElements.into(TypeDescriptor.of(String.class)).via(x -> {
                    System.out.println(x);
                    for(int i = 0; i < 100000; i++){

                        i++;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return x;
                }))
        //.apply("Vision Api", ParDo.of(VISION_FN))
        //.apply("Scrape API", ParDo.of(SCRAPE_FN))
        //.apply("SaveToFireStore", ParDo.of(new FirestoreWriteDoFn<>()))
        ;
        p.run();
    }


}
