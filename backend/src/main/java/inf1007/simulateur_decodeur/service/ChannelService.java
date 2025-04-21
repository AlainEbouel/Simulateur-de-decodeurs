package inf1007.simulateur_decodeur.service;

import inf1007.simulateur_decodeur.model.Channel;
import inf1007.simulateur_decodeur.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    public Channel findById(Long id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
    }

    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    public void delete(Long id) {
        channelRepository.deleteById(id);
    }

    public List<Channel> getChannelsByDecoderId(Long decoderId) {
        return channelRepository.findByDecoderId(decoderId);
    }
}
