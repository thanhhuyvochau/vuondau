package fpt.capstone.vuondau.service.Impl;


import fpt.capstone.vuondau.entity.InfoFindTutor;
import fpt.capstone.vuondau.entity.InfoFindTutorAccount;
import fpt.capstone.vuondau.entity.InfoFindTutorAccountKey;
import fpt.capstone.vuondau.entity.dto.InfoFindTutorDto;
import fpt.capstone.vuondau.repository.AccountRepository;
import fpt.capstone.vuondau.service.IInfoFindTutorService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class InfoFindTutorServiceImpl implements IInfoFindTutorService {

    private final AccountRepository accountRepository ;

    public InfoFindTutorServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Boolean registerFindTutor(InfoFindTutorDto infoFindTutorDto) {
        InfoFindTutor infoFindTutor = ObjectUtil.copyProperties(infoFindTutorDto, new InfoFindTutor(), InfoFindTutor.class);
        List<InfoFindTutorAccount> infoFindTutorAccountList = new ArrayList<>();


     return null ;
    }
}
