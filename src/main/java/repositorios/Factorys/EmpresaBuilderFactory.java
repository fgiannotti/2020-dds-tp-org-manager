package repositorios.Factorys;

import repositorios.Builders.EmpresaBuilder;
import entidades.Estrategias.CategorizadorEmpresa;

public class EmpresaBuilderFactory {

    private CategorizadorEmpresa categorizadorEmpresa;
    private EmpresaBuilder builder;

    public EmpresaBuilderFactory() {
        this.categorizadorEmpresa = new CategorizadorEmpresa();
    }

    public EmpresaBuilder createBuilder(){

        EmpresaBuilder builder = new EmpresaBuilder();

        builder.setCategorizadorEmpresa(this.categorizadorEmpresa);

        return builder;
    }

    public CategorizadorEmpresa getCategorizadorEmpresa() {
        return categorizadorEmpresa;
    }

    public void setCategorizadorEmpresa(CategorizadorEmpresa categorizadorEmpresa) {
        this.categorizadorEmpresa = categorizadorEmpresa;
    }
}
