package com.project.blog.test;

import com.project.blog.model.RoleType;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//html파일이 아니라 data를 리턴해주는 controller
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입
    private UserRepository userRepository;
    @GetMapping("/dummy/users")
    public List<User> list(){
        List<User> user = userRepository.findAll();
        return user;
    }
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
 /*       User user = pagingUser.getContent().stream().filter(p -> "coss".equals(p.getUsername()))
               .findFirst().orElseThrow(() -> new IllegalArgumentException("없다 그런거"));
        List<User> users = pagingUser.get().filter(user -> "love".equals(user.getUsername())).collect(Collectors.toList());
        if(pagingUser.isLast()){}   */
        return users;

    }


    //주소로 파라메터를 전달 받을 수 있음.
    //http:/localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        //데이터베이스에서 못찾아오게 되면 null이됌
        //Optional로 너의 user 객체를 감싸서 가져올테니 null 인지 아닌지 판단해서 return 해!!

        //람다식
       User user=  userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
           @Override
           public IllegalArgumentException get() {
               return new IllegalArgumentException("해당 유저는 없습니다. id" + id);
           }
       });
       //요청 : 웹브라우저
       //user 객체 = 자바 오브젝트
        //변환 (웹브라우저가 이해할 수 있는 데이터) ->json (Gson 라이브러리)
        //스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        //만약에 자바 오브젝트를 리턴하게 되면 Jacksoon 라이브러리로 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저로 전달해줌.
       return user;

    }


    //http://localhost:8000/blog/dummy/join (요청)
    //http의 body에 username, password, emaail 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return " 회원가입이 완료되었습니다." + user.getUsername();
    }
}
