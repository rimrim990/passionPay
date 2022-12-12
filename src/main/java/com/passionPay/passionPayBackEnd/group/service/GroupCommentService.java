package com.passionPay.passionPayBackEnd.group.service;

import com.passionPay.passionPayBackEnd.group.dto.GroupCommentDto;
import com.passionPay.passionPayBackEnd.group.domain.GroupPostComment;
import com.passionPay.passionPayBackEnd.group.repository.GroupCommentRepository;
import com.passionPay.passionPayBackEnd.group.repository.GroupMemberRepository;
import com.passionPay.passionPayBackEnd.group.repository.GroupPostRepository;
import com.passionPay.passionPayBackEnd.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupCommentService {

    private final GroupCommentRepository groupCommentRepository;
    private final GroupPostRepository groupPostRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupCommentService(GroupCommentRepository groupCommentRepository, GroupPostRepository groupPostRepository, GroupMemberRepository groupMemberRepository) {
        this.groupCommentRepository = groupCommentRepository;
        this.groupPostRepository = groupPostRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    // save group comment
    public GroupCommentDto save(Long postId, String content, Long memberId) {
        return groupPostRepository.findById(postId)
                .map(groupPost -> {
                    // post
                    GroupPostComment groupPostComment = groupMemberRepository
                            .findByMemberIdAndGroupId(memberId, groupPost.getGroupMember().getGroupId())
                            // group member
                            .map(groupMember ->
                                GroupPostComment.builder()
                                        .content(content)
                                        .groupPost(groupPost)
                                        .groupMember(groupMember)
                                        .build())
                            .orElseThrow(RuntimeException::new);
                    // save
                    groupCommentRepository.save(groupPostComment);
                    return GroupCommentDto.from(groupPostComment);
                })
                .orElseThrow(RuntimeException::new);
    }

    // get group comments by post id
    public List<GroupCommentDto> getGroupCommentByPostId(Long postId) {
        return groupPostRepository.findById(postId)
                .map(groupPost ->
                    groupCommentRepository.findByGroupPost(groupPost)
                            // group comment dto 로 변환
                            .stream()
                            .map(GroupCommentDto::from)
                            .collect(Collectors.toList()))
                .orElseThrow(RuntimeException::new);
    }

    // update comment
    public GroupCommentDto updateGroupComment(Long groupCommentId, String content) {
        return groupCommentRepository.findById(groupCommentId)
                .map(groupPostComment -> {
                  groupPostComment.setContent(content);
                  return GroupCommentDto.from(groupPostComment);
                }).orElseThrow(RuntimeException::new);
    }

    // delete comment
    public boolean delete(Long groupCommentId) {
        return groupCommentRepository.findById(groupCommentId)
                .map(groupPostComment -> {
                    if (groupPostComment.getGroupMember().getMemberId() != SecurityUtil.getCurrentMemberId())
                        return false;
                    // 연관관계 제거
                    groupCommentRepository.delete(groupPostComment);
                    return true;
                }).orElseThrow(RuntimeException::new);
    }
}
