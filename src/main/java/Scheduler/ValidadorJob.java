package Scheduler;

import Estrategias.Validador;
import Operaciones.OperacionEgreso;
import org.quartz.*;

public class ValidadorJob implements Job{
    public static Validador validador;
    public static OperacionEgreso egreso;

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
        validador = (Validador)dataMap.get("validador");
        egreso = (OperacionEgreso) dataMap.get("egreso");
        System.out.println("El job empezo en: " + jobContext.getFireTime());
        validador.validar(egreso);
        System.out.println("El próximo job se ejecutará: " + jobContext.getNextFireTime());

    }
}