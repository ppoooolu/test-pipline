import javaposse.jobdsl.dsl.Job
//import static org.Fleets
import io.unguiculus.jobdsl.CreateMulitibranchPipelineJob

Job collectParamsJob = new CreateMulitibranchPipelineJob().build(this)