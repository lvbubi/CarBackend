package hu.bbb.carbackend.controller;

import hu.bbb.carbackend.service.CarService;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/asd")
public class HelloController {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private CarService carService;

    @GetMapping("cica")
    public String asd(){
        return "punci";
    }

    @GetMapping("test")
    public String asd2(){
        /*jobScheduler.enqueue(() -> {
            main("--project=car-dataflow",
                    "--bucket=car-dataflow",
                    "--functionBase=https://europe-west3-car-dataflow.cloudfunctions.net");

        });*/

        JobId jobId = jobScheduler.enqueue(() -> carService.executeCarDataflow());

        System.out.println("finished");
        return jobId.asUUID().toString();
    }
}
