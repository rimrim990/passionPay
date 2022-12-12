package com.passionPay.passionPayBackEnd.group.service;

import com.passionPay.passionPayBackEnd.group.dto.GroupPostDto;
import com.passionPay.passionPayBackEnd.group.payload.CreateGroupPostPayload;
import com.passionPay.passionPayBackEnd.group.domain.GroupPost;
import com.passionPay.passionPayBackEnd.group.repository.*;
import com.passionPay.passionPayBackEnd.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupPostService {
    private final GroupPostRepository groupPostRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final GroupCommentRepository groupCommentRepository;
    private final GroupLikeRepository groupLikeRepository;

    public GroupPostService(GroupPostRepository groupPostRepository, GroupMemberRepository groupMemberRepository, GroupRepository groupRepository, GroupCommentRepository groupCommentRepository, GroupLikeRepository groupLikeRepository) {
        this.groupPostRepository = groupPostRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
        this.groupCommentRepository = groupCommentRepository;
        this.groupLikeRepository = groupLikeRepository;
    }

    // post 저장
    public GroupPostDto save(CreateGroupPostPayload createGroupPostPayload, Long groupId) {
        return groupMemberRepository.findByMemberIdAndGroupId(createGroupPostPayload.getMemberId(), groupId)
                .map(groupMember -> {
                    GroupPost groupPost = CreateGroupPostPayload.from(createGroupPostPayload, groupMember);
                    groupPostRepository.save(groupPost);
                    return GroupPostDto.from(groupPost);
                }).orElseThrow(RuntimeException::new);
    }

    // group id 로 group post 조회 ( 내림차순 )
    public List<GroupPostDto> getGroupPostByGroupId(Long groupId) {
        return groupRepository.findById(groupId)
                .map(group ->
                    groupPostRepository.findByGroupIdByOrderByGroupIdDesc(groupId)
                            .stream().map(groupPost -> GroupPostDto.from(groupPost))
                            .collect(Collectors.toList()))
                .orElseThrow(RuntimeException::new);
    }

    // post 수정 (photoUrl, content)
    public GroupPostDto update(CreateGroupPostPayload createGroupPostPayload, Long groupPostId) {
        return groupPostRepository.findById(groupPostId)
                .map(groupPost -> {
                    // update
                    groupPost.setContent(createGroupPostPayload.getContent());
                    groupPost.setPhotoUrl(createGroupPostPayload.getPhotoUrl());

                    // save
                    groupPostRepository.save(groupPost);
                    return GroupPostDto.from(groupPost);
                }).orElseThrow(RuntimeException::new);
    }

    // post 삭제
    public boolean delete(Long groupPostId) {
        return groupPostRepository.findById(groupPostId)
                .map(groupPost -> {
                    if (!SecurityUtil.getCurrentMemberId().equals(groupPost.getGroupMember().getMemberId()))
                        return false;

                    // post 제거 ( group comment -> group post )
                    groupPostRepository.delete(groupPost);

                    // 연관관계 제거
                    groupCommentRepository.deleteByGroupPost(groupPost);
                    groupLikeRepository.deleteByGroupPost(groupPost);

                    return true;
                }).orElse(false);
    }
}
