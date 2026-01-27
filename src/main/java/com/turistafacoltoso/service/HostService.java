package com.turistafacoltoso.service;

import java.util.List;

import com.turistafacoltoso.model.StatisticheHost;
import com.turistafacoltoso.repository.HostRepository;

public class HostService {

    private final HostRepository hostRepository;

    public HostService() {
        this.hostRepository = new HostRepository();
    }

    public List<StatisticheHost> getHostPiuPrenotazioniUltimoMese() {
        return hostRepository.findHostConPiuPrenotazioniUltimoMese();
    }

    public List<StatisticheHost> getSuperHost() {
        return hostRepository.findSuperHost();
    }
}
