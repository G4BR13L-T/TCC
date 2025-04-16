package com.tcc.TCC.model;

public class M_Resposta {
    private boolean sucesso;
    private String mensagem;
    private Object object;

    public M_Resposta() {
        sucesso = false;
        mensagem = "";
        object = null;
    }

    public M_Resposta(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        object = null;
    }

    public M_Resposta(boolean sucesso, String mensagem, Object object) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.object = object;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
