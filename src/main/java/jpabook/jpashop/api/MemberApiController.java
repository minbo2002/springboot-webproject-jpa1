package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController  //@Controller + @ResponseBody
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원등록 (엔티티 변경하면 api 스펙 바뀜)
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    // 회원등록 (엔티티 변경하면 api 스펙 안바뀐다 + 엔티티를 외부에 노출하지 않는다)
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.name);

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    // 회원수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    // 회원목록 (엔티티 변경하면 api 스펙 바뀜)
    @GetMapping("/api/v1/members")
    public List<Member> memberV1() {

        return memberService.findMembers();
    }

    // 회원목록 (엔티티 변경하면 api 스펙 안바뀐다 + 엔티티를 외부에 노출하지 않는다)
    @GetMapping("/api/v2/members")
    public Result memberV2() {

        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }


    @Data
    static class CreateMemberRequest {

        @NotEmpty
        private String name;

    }

    @Data  // 등록된 Id를 반환하는 용도
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
