package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.ClassType;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.ClassTypeRequest;
import fpt.capstone.vuondau.entity.response.ClassTypeResponse;
import fpt.capstone.vuondau.repository.ClassTypeRepository;
import fpt.capstone.vuondau.service.IClassService;
import fpt.capstone.vuondau.service.IClassTypeService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassServiceImpl implements IClassService {

}
