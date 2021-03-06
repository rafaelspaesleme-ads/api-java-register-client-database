package br.com.xyz.webclient.entity;

import javax.persistence.*;

@Entity
@Table(name = "controle_porta")
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer primaryPort;
    private Integer lastPort;
    private Integer newPort;

    public Port() {
    }

    public Port(Long id, Integer primaryPort, Integer lastPort, Integer newPort) {
        this.id = id;
        this.primaryPort = primaryPort;
        this.lastPort = lastPort;
        this.newPort = newPort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrimaryPort() {
        return primaryPort;
    }

    public void setPrimaryPort(Integer primaryPort) {
        this.primaryPort = primaryPort;
    }

    public Integer getLastPort() {
        return lastPort;
    }

    public void setLastPort(Integer lastPort) {
        this.lastPort = lastPort;
    }

    public Integer getNewPort() {
        return newPort;
    }

    public void setNewPort(Integer newPort) {
        this.newPort = newPort;
    }
}
