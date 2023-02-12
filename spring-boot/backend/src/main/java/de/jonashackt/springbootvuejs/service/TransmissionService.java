package de.jonashackt.springbootvuejs.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stil4m.transmission.api.TransmissionRpcClient;
import nl.stil4m.transmission.api.domain.RemoveTorrentInfo;
import nl.stil4m.transmission.api.domain.TorrentInfo;
import nl.stil4m.transmission.api.domain.ids.Ids;
import nl.stil4m.transmission.api.domain.ids.NumberListIds;
import nl.stil4m.transmission.api.domain.ids.OmittedIds;
import nl.stil4m.transmission.api.torrent.TorrentStatus;
import nl.stil4m.transmission.rpc.RpcClient;
import nl.stil4m.transmission.rpc.RpcConfiguration;
import nl.stil4m.transmission.rpc.RpcException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Service
public class TransmissionService {
    final private TransmissionRpcClient rpcClient;

    TransmissionService() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RpcConfiguration rpcConfiguration = new RpcConfiguration();
        rpcConfiguration.setHost(URI.create("http://192.168.86.2:9091/transmission/rpc/"));
        RpcClient client = new RpcClient(rpcConfiguration, objectMapper);
        rpcClient = new TransmissionRpcClient(client);
    }

    public List<TorrentInfo> getTorrents() throws RpcException {
        return rpcClient.getAllTorrentsInfo().getTorrents();
    }

    public int removeTorrent(String id) throws InterruptedException, IOException {
        //Process proc = new ProcessBuilder("ssh", "ubuntu@192.168.86.2", "transmission-remote -t " + id + " --remove-and-delete").start();
        Process proc = Runtime.getRuntime().exec("transmission-remote -t " + id + " --remove-and-delete");
        return proc.waitFor();
    }
}
