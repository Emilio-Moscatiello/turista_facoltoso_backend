package com.turistafacoltoso.service;

import java.util.List;
import java.util.UUID;

import com.turistafacoltoso.dto.StatisticheHostDTO;
import com.turistafacoltoso.model.Host;
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

    public List<Host> getAllHost() {
        return hostRepository.findAll();
    }

    public Host getHostById(UUID id) {
        return hostRepository.findById(id);
    }

    public Host createHost(Host host) {
        hostRepository.save(host);
        return host;
    }

    public Host updateHost(UUID id, Host host) {
        hostRepository.update(id, host);
        return host;
    }

    public void deleteHost(UUID id) {
        hostRepository.delete(id);
    }

}
