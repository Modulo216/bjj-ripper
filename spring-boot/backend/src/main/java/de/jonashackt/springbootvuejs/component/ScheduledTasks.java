package de.jonashackt.springbootvuejs.component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.jonashackt.springbootvuejs.domain.Ratio;
import de.jonashackt.springbootvuejs.repository.RatioRepository;
import de.jonashackt.springbootvuejs.service.RpcService;
import org.h2.util.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private RpcService rpcService;

    @Autowired
    private RatioRepository ratioRepository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    ObjectMapper mapper;

    ScheduledTasks() {
        mapper = new ObjectMapper();
    }

    // 5 hours
    @Scheduled(fixedRate = 5*60*60*1000)
    public void updateTorrentRatio() throws JsonProcessingException {
        JsonNode obj = mapper.readTree(rpcService.getTorrents());

        ArrayNode arr = (ArrayNode) obj.get("arguments").get("torrents");
        for(JsonNode torrent : arr) {
            Ratio ratio= new Ratio(torrent.get("uploadRatio").asDouble(), torrent.get("id").asInt(), new Date());
            ratioRepository.save(ratio);
        }
    }
}