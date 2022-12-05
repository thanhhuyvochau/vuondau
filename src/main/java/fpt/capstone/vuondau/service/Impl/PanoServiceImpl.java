package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Pano;
import fpt.capstone.vuondau.entity.Resource;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.PanoDto;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.GetPanoResponse;
import fpt.capstone.vuondau.repository.PanoRepository;
import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.service.PanoService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import fpt.capstone.vuondau.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class PanoServiceImpl implements PanoService {

    private final ResourceRepository resourceRepository;

    private final MessageUtil messageUtil;

    private final PanoRepository panoRepository ;

    public PanoServiceImpl(ResourceRepository resourceRepository, MessageUtil messageUtil, PanoRepository panoRepository) {
        this.resourceRepository = resourceRepository;
        this.messageUtil = messageUtil;
        this.panoRepository = panoRepository;
    }

    @Override
    public GetPanoResponse createNewPano(PanoRequest panoRequest) {
        Pano pano = new Pano();
        pano.setName(panoRequest.getName());
        pano.setPublishDate(panoRequest.getPublishDate());
        pano.setExpirationDate(panoRequest.getExpirationDate());


        pano.setLinkUrl(panoRequest.getLinkUrl());

        Resource resource = resourceRepository.findById(panoRequest.getResourceId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("khong tim thay anh") + panoRequest.getResourceId()));
        pano.setResource(resource);


        pano.setVisible(panoRequest.getVisible());


        Pano panoSaved = panoRepository.save(pano);


        return convertPanoToPanoResponse(panoSaved);
    }

    @Override
    public ApiPage<GetPanoResponse> getAllPano(Pageable pageable) {
        Page<Pano> allPano = panoRepository.findAll(pageable);
        return PageUtil.convert(allPano.map(PanoServiceImpl::convertPanoToPanoResponse
        ));

    }

    @Override
    public List<GetPanoResponse> getPanos() {
        List<Pano> all = panoRepository.findAll();
        List<GetPanoResponse> panoResponses = new ArrayList<>() ;
        all.forEach(pano -> {
            panoResponses.add(convertPanoToPanoResponse(pano)) ;
        });
       return panoResponses ;
    }

    public static GetPanoResponse convertPanoToPanoResponse(Pano pano) {
        GetPanoResponse getPanoResponse = new GetPanoResponse();
        getPanoResponse.setId(pano.getId());
        getPanoResponse.setName(pano.getName());
        getPanoResponse.setPublishDate(pano.getPublishDate());
        getPanoResponse.setExpirationDate(pano.getExpirationDate());

//        getPanoResponse.setResource(ObjectUtil.copyProperties(pano.getResource(), new ResourceDto(), ResourceDto.class));
        getPanoResponse.setVisible(pano.getVisible());
        if (pano.getResource()!= null){
            getPanoResponse.setLinkUrl( pano.getResource().getUrl());
        }

        getPanoResponse.setStatus(getStatus(pano));
        return getPanoResponse;
    }

    private static String getStatus(Pano pano) {
        Instant now  = Instant.now() ;
        Instant expirationDate = pano.getExpirationDate();
        Instant publishDate = pano.getPublishDate();
        if (publishDate != null && now.isBefore(publishDate)) {
            return "WAITING_PUBLISH" ;
        }
        if (expirationDate != null && now.isAfter(expirationDate)) {
            return "EXPIRED" ;
        }
        if (publishDate != null && expirationDate != null) {
            if (now.isAfter(publishDate) && now.isBefore(expirationDate)) {
                return "PUBLISHING";
            }
        }
        return null;
    }

    @Override
    public Long deletePano(Long panoId) {
        Pano pano = panoRepository.findById(panoId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND)
                .withMessage(messageUtil.getLocalMessage("Không tìm thấy Pano") + panoId));
        panoRepository.delete(pano);
        return panoId;
    }

    @Override
    public GetPanoResponse updatePano(Long id, PanoDto panoDto) {
        Pano oldPano = panoRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Không tìm thấy Pano") + id));
        oldPano.setName(panoDto.getName());
        if (panoRepository.existsByName(panoDto.getName()) && !oldPano.getName().equals(panoDto.getName())) {
            throw ApiException.create(HttpStatus.BAD_REQUEST)
                    .withMessage(messageUtil.getLocalMessage("Name đã tồn tại"));
        }
        oldPano.setPublishDate(panoDto.getPublishDate());

        Resource resource = resourceRepository.findById(panoDto.getImageId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("Hình không có trong hệ thống") + panoDto.getImageId()));
        oldPano.setResource(resource);

        oldPano.setVisible(panoDto.getVisible()); ;

        Pano newPano = panoRepository.save(oldPano);
        return convertPanoToPanoResponse(newPano);

    }

}

