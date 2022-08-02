package com.jinho;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.StepLocator;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.StepRunner;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class CustomJobLauncherTestUtils {

    private final SecureRandom secureRandom = new SecureRandom();

    protected final Log logger = LogFactory.getLog(getClass());

    private final JobLauncher jobLauncher;

    private final JobRepository jobRepository;

    private StepRunner stepRunner;

    private Job job;

    public CustomJobLauncherTestUtils(final JobLauncher jobLauncher, final JobRepository jobRepository) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public Job getJob() {
        return job;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public JobExecution launchJob() throws Exception {
        return this.launchJob(this.getUniqueJobParameters());
    }

    public JobExecution launchJob(JobParameters jobParameters) throws Exception {
        return getJobLauncher().run(this.job, jobParameters);
    }

    public JobParameters getUniqueJobParameters() {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("random", new JobParameter(this.secureRandom.nextLong()));
        return new JobParameters(parameters);
    }
    public JobParametersBuilder getUniqueJobParametersBuilder() {
        return new JobParametersBuilder(this.getUniqueJobParameters());
    }

    protected StepRunner getStepRunner() {
        if (this.stepRunner == null) {
            this.stepRunner = new StepRunner(getJobLauncher(), getJobRepository());
        }
        return this.stepRunner;
    }

    public JobExecution launchStep(String stepName) {
        return this.launchStep(stepName, this.getUniqueJobParameters(), null);
    }

    public JobExecution launchStep(String stepName, ExecutionContext jobExecutionContext) {
        return this.launchStep(stepName, this.getUniqueJobParameters(), jobExecutionContext);
    }

    public JobExecution launchStep(String stepName, JobParameters jobParameters) {
        return this.launchStep(stepName, jobParameters, null);
    }

    public JobExecution launchStep(String stepName, JobParameters jobParameters, @Nullable ExecutionContext jobExecutionContext) {
        if (!(job instanceof StepLocator)) {
            throw new UnsupportedOperationException("Cannot locate step from a Job that is not a StepLocator: job="
                + job.getName() + " does not implement StepLocator");
        }
        StepLocator locator = (StepLocator) this.job;
        Step step = locator.getStep(stepName);
        if (step == null) {
            step = locator.getStep(this.job.getName() + "." + stepName);
        }
        if (step == null) {
            throw new IllegalStateException("No Step found with name: [" + stepName + "]");
        }
        return getStepRunner().launchStep(step, jobParameters, jobExecutionContext);
    }
}
