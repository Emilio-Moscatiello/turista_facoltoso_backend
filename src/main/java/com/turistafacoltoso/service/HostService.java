package com.turistafacoltoso.service;

import java.util.List;

import com.turistafacoltoso.dto.StatisticheHostDTO;
import com.turistafacoltoso.repository.HostRepository;

public class HostService {

    private final HostRepository hostRepository;

    public HostService() {
        this.hostRepository = new HostRepository();
    }

    public List<StatisticheHostDTO> getHostPiuPrenotazioniUltimoMese() {
        return hostRepository.findHostConPiuPrenotazioniUltimoMese();
    }

    public List<StatisticheHostDTO> getSuperHost() {
        return hostRepository.findSuperHost();
    }
}
