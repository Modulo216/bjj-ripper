package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Ratio;
import de.jonashackt.springbootvuejs.repository.RatioRepository;
import de.jonashackt.springbootvuejs.service.RpcService;
import de.jonashackt.springbootvuejs.service.TransmissionService;
import nl.stil4m.transmission.api.domain.TorrentInfo;
import nl.stil4m.transmission.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import oshi.SystemInfo;

import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
@Scope("session")
@CrossOrigin(origins = {"http://dreadgeneration.ddns.net:65216/", "http://localhost:65216"})
public class BackendController {
    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private TransmissionService transmissionService;

    @Autowired
    private RpcService rpcService;

    @Autowired
    private RatioRepository ratioRepository;

    @ResponseBody
    @RequestMapping(path = "/remove-torrent/{id}")
    @Transactional
    public Integer rmTorrent(@PathVariable("id") String id) throws InterruptedException, IOException {
        ratioRepository.deleteByTorrentId(Integer.valueOf(id));
        return transmissionService.removeTorrent(id);
    }

    @ResponseBody
    @RequestMapping(path = "/getTorrents")
    public String callRPC() {
        return rpcService.getTorrents();
    }

    @ResponseBody
    @RequestMapping(path = "/getRatio/{id}")
    public List<Ratio> getRatio(@PathVariable("id") Integer id) {
        return ratioRepository.findTop25ByTorrentIdOrderByIdDesc(id);
    }

    @ResponseBody
    @RequestMapping(path = "/uptime")
    public String uptime() throws IOException {
        long seconds = new SystemInfo().getOperatingSystem().getSystemUptime();
        long p1 = seconds % 60;
        long p2 = seconds / 60;
        long p3 = p2 % 60;
        p2 = p2 / 60;
        return String.join(",", String.valueOf(p2), String.valueOf(p3), String.valueOf(p1));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                LOG.info(String.valueOf(file));
                File dir = new File("/media/usb/torrents");
                File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                LOG.info("Server File Location="
                        + serverFile.getAbsolutePath());

                return "You successfully uploaded file";
            } catch (Exception e) {
                return "You failed to upload " + e.getMessage();
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }

    @ResponseBody
    @GetMapping(path = "/derp/{pass}")
    public Integer auth(@PathVariable("pass") String pass) throws RpcException {
        Integer retVal = null;
        if(pass.equals("Chaoticgood")) {
            retVal = 1;
        }
        return retVal;
    }

}
