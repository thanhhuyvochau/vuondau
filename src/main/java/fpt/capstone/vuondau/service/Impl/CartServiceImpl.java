package fpt.capstone.vuondau.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.*;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.RoleDto;
import fpt.capstone.vuondau.entity.dto.SubjectDto;
import fpt.capstone.vuondau.entity.response.*;
import fpt.capstone.vuondau.repository.*;
import fpt.capstone.vuondau.service.ICartService;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {

    private final CourseRepository courseRepository;

    private final CartRepository cartRepository;

    private final AccountRepository accountRepository;

    private final CourseServiceImpl courseServiceImpl;

    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CourseRepository courseRepository, CartRepository cartRepository, AccountRepository accountRepository, CourseServiceImpl courseServiceImpl, CartItemRepository cartItemRepository) {
        this.courseRepository = courseRepository;
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
        this.courseServiceImpl = courseServiceImpl;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponse addCourseIntoCart(long courseId, Long studentId) throws JsonProcessingException {


        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay course") + courseId));

        Account student = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay student") + studentId));

        Cart cart = cartRepository.findByStudent(student).orElse(new Cart());

        cart.setName(student.getId() + "" + student.getUsername());
        cart.setStudent(student);

        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        List<CartItemCourse> cartItemCourseList = new ArrayList<>();
        CartItemCourse cartItemCourse = new CartItemCourse();
        CartItemCourseKey cartItemCourseKey = new CartItemCourseKey();
        cartItemCourseKey.setCartItemId(cartItem.getId());
        cartItemCourseKey.setCourseId(courseId);
        cartItemCourse.setId(cartItemCourseKey);
        cartItemCourse.setCourse(course);
        cartItemCourse.setCartItem(cartItem);
        cartItemCourseList.add(cartItemCourse);
        cartItem.setCartItemCourses(cartItemCourseList);
        cartItemList.add(cartItem);
        cart.getCartItem().addAll(cartItemList);
        cartItemRepository.saveAll(cartItemList);
        cartRepository.save(cart);

        /// Response ;

        CartResponse cartResponse = new CartResponse();
        if (student.getCart() != null) {
            cartResponse.setCartId(student.getCart().getId());
        }

        AccountResponse accountResponse = ObjectUtil.copyProperties(student, new AccountResponse(), AccountResponse.class);
        accountResponse.setRole(ObjectUtil.copyProperties(student.getRole(), new RoleDto(), RoleDto.class));
        cartResponse.setStudent(accountResponse);


        List<CartItemTopicResponse> cartItemTopicList = new ArrayList<>();
        CartItemTopicResponse cartItemTopic = new CartItemTopicResponse();
        CourseDetailResponse courseDetailResponse = courseServiceImpl.convertCourseToCourseDetailResponse(course);
        cartItemTopic.setCourse(courseDetailResponse);
        cartItemTopicList.add(cartItemTopic);
        cartResponse.setCartItemTopic(cartItemTopicList);


        return cartResponse;
    }

    @Override
    public CartCourseResponse getCourseIntoCart(long studentId) {
        Account student = accountRepository.findById(studentId)
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(("Khong tim thay student") + studentId));

        CartCourseResponse cartCourseResponse = new CartCourseResponse();
        List<CourseResponse> courseResponseList = new ArrayList<>();
        Cart cart = student.getCart();
        if (cart != null) {


            BigDecimal totalPrice  = BigDecimal.ZERO ;
            int quantity = 0 ;
            cartCourseResponse.setCartId(cart.getId());
            List<CartItem> cartItemList = cart.getCartItem();
            for (CartItem cartItem : cartItemList) {


                CourseResponse courseResponse = new CourseResponse();
                List<CartItemCourse> cartItemCourses = cartItem.getCartItemCourses();

                for (CartItemCourse cartItemCourse : cartItemCourses) {
                    Course course = cartItemCourse.getCourse();
                    if (course != null) {
                        courseResponse.setId(course.getId());
                        courseResponse.setCourseName(course.getName());
                        if (course.getResource() != null) {
                            courseResponse.setImage(course.getResource().getUrl());
                        }
//                        courseResponse.setUnitPriceCourse(course.getUnitPrice());
//                        courseResponse.setFinalPriceCourse(course.getFinalPrice());
                        if (!course.getTeacherCourses().isEmpty()) {
                            Account account = course.getTeacherCourses().stream().map(TeacherCourse::getAccount).findFirst().get();

                            courseResponse.setTeacherName(account.getName());
                        }
//                        if (course.getFinalPrice()!= null){
//                            totalPrice = totalPrice.add(course.getFinalPrice());
//                        }
                        quantity+= 1 ;

                        courseResponse.setSubject(ObjectUtil.copyProperties(course.getSubject(), new SubjectResponse() , SubjectResponse.class));

                        courseResponseList.add(courseResponse);

                    }
                }
            }

            cartCourseResponse.setQuantity(quantity);
            cartCourseResponse.setTotalPrice(totalPrice);
            cartCourseResponse.setCourses(courseResponseList);
        }


        return cartCourseResponse;
    }

//
//    public CartResponse convertCartToCartResponse(Cart cart) {
//        CartResponse cartResponse = new CartResponse();
//        cartResponse.setCartId(cart.getId());
//        Account student = cart.getStudent();
//        if (student != null) {
//            AccountResponse accountResponse = ObjectUtil.copyProperties(student, new AccountResponse(), AccountResponse.class);
//            accountResponse.setRole(ObjectUtil.copyProperties(student.getRole(), new RoleDto(), RoleDto.class));
//            cartResponse.setStudent(accountResponse);
//        }
//
//
//    }
}
