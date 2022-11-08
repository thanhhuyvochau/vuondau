package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.common.ApiPage;
import org.springframework.data.domain.Page;


public class PageUtil {

    public static <T> ApiPage<T> convert(Page<T> page) {
        ApiPage<T> apiPage = new ApiPage<>();
        apiPage.setTotalItems(page.getTotalElements());
        apiPage.setTotalPages(page.getTotalPages());
        apiPage.setCurrentPage(page.getNumber());
        apiPage.setPageSize(page.getSize());
        apiPage.setPageItemSize(page.getNumberOfElements());
        apiPage.setFirst(page.isFirst());
        apiPage.setLast(page.isLast());
        apiPage.setItems(page.getContent());
        return apiPage;
    }

}
