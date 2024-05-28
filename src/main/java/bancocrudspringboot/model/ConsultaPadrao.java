package bancocrudspringboot.model;

public class ConsultaPadrao {

    private String campo;
    private String operador;
    private String valor1;
    private String valor2;

    public ConsultaPadrao() {

    }

    public ConsultaPadrao(String campo, String operador, String valor1, String valor2) {
        this.campo = campo;
        this.operador = operador;
        this.valor1 = valor1;
        this.valor2 = valor2;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }
}
