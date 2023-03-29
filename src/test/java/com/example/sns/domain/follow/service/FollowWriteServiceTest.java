package com.example.sns.domain.follow.service;

import com.example.sns.IntegrationTest;
import com.example.sns.domain.member.dto.MemberDto;
import com.example.sns.domain.member.repository.MemberRepository;
import com.example.sns.factory.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class FollowWriteServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowWriteService followWriteService;

    @DisplayName("본인 계정을 팔로우 할 수 없다")
    @Test
    public void testSelfFollow() {
        var member = createMemberDto();

        assertThrows(
                IllegalArgumentException.class,
                () -> followWriteService.create(member, member)
        );
    }

    @DisplayName("팔로우 생성 테스트")
    @Test
    public void testCreate() {
        var fromMember = createMemberDto();
        var toMember = createMemberDto();

        var result = followWriteService.create(fromMember, toMember);

        assertNotNull(result.getId());
        assertEquals(fromMember.id(), result.getFromMemberId());
        assertEquals(toMember.id(), result.getToMemberId());
    }

    @DisplayName("fromMember, toMember 중복 팔로우 테스트")
    @Test
    public void testDuplicatedFollow() {
        var fromMember = createMemberDto();
        var toMember = createMemberDto();

        followWriteService.create(fromMember, toMember);
        assertThrows(
                DuplicateKeyException.class,
                () -> followWriteService.create(fromMember, toMember)
        );
    }

    private MemberDto createMemberDto() {
        return MemberFixtureFactory.createDto();
    }
}