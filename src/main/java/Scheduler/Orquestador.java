package Scheduler;

import Estrategias.Validador;
import Operaciones.OperacionEgreso;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Orquestador {
    private int tiempo;

    public void orquestraJob(Validador validador, OperacionEgreso egreso) {
        SchedulerFactory schedFactory = new StdSchedulerFactory();
        try {
            org.quartz.Scheduler sched = schedFactory.getScheduler();
            JobDetail job = JobBuilder.newJob(ValidadorJob.class).withIdentity("validadorJob", "grupo1").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("triggerGrupo1", "grupo1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(tiempo).repeatForever()).build();
            job.getJobDataMap().put("validador", validador);
            job.getJobDataMap().put("egreso", egreso);
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Orquestador (int tiempoEnSegundos) {
        this.tiempo = tiempoEnSegundos;
    }
}