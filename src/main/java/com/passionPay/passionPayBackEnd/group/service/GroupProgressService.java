package com.passionPay.passionPayBackEnd.group.service;

import com.passionPay.passionPayBackEnd.group.dto.GroupProgressDto;
import com.passionPay.passionPayBackEnd.member.dto.MemberInfoDto;
import com.passionPay.passionPayBackEnd.group.domain.Group;
import com.passionPay.passionPayBackEnd.group.domain.GroupMissionProgress;
import com.passionPay.passionPayBackEnd.group.repository.GroupMemberRepository;
import com.passionPay.passionPayBackEnd.group.repository.GroupMissionRepository;
import com.passionPay.passionPayBackEnd.group.repository.GroupProgressRepository;
import com.passionPay.passionPayBackEnd.util.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class GroupProgressService {

    private final GroupProgressRepository groupProgressRepository;
    private final GroupMissionRepository groupMissionRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupProgressService(GroupProgressRepository groupProgressRepository, GroupMissionRepository groupMissionRepository, GroupMemberRepository groupMemberRepository) {
        this.groupProgressRepository = groupProgressRepository;
        this.groupMissionRepository = groupMissionRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    // save
    public GroupProgressDto save(Long memberId, Long groupMissionId) {
        return groupMissionRepository.findById(groupMissionId)
                .map(groupMission ->
                    groupMemberRepository.findByMemberIdAndGroupId(memberId, groupMission.getGroup().getId())
                            .map(groupMember -> {
                                GroupMissionProgress groupMissionProgress = GroupMissionProgress.builder()
                                        .groupMember(groupMember)
                                        .complete(false)
                                        .groupMission(groupMission)
                                        .build();
                                groupProgressRepository.save(groupMissionProgress);
                                return GroupProgressDto.builder()
                                        .groupProgressId(groupMissionProgress.getId())
                                        .memberInfo(MemberInfoDto.of(groupMember.getMember()))
                                        .completed(groupMissionProgress.isComplete())
                                        .missionName(groupMissionProgress.getGroupMission().getContent())
                                        .build();
                            }).orElseThrow(RuntimeException::new))
                .orElseThrow(RuntimeException::new);
    }

    // find
    public int getMyCount(Group group) {
        return groupMemberRepository.findByMemberIdAndGroupId(SecurityUtil.getCurrentMemberId(), group.getId())
                .map(groupMember -> {
                    // 내가 성공한 미션 개수
                    int count = groupProgressRepository.findCountByGroupMemberAndGroupMission(groupMember.getId(), group);
                    // 미션 개수 총 합
                    int missionCount = groupMissionRepository.findByGroup(group).size();
                    return count / missionCount * 100;
                }).orElseThrow(RuntimeException::new);
    }

    public int getGroupAvgCount(Group group) {
        int count = groupProgressRepository.findCountByGroupMission(group);
        int missionCount = groupMissionRepository.findByGroup(group).size();
        int memberCount = group.getGroupMembers().size();
        return (count / missionCount) / memberCount * 100;
    }

    // update
    public GroupProgressDto update(Long groupProgressId, boolean complete) {
        return groupProgressRepository.findById(groupProgressId)
                .map(groupMissionProgress -> {
                    if (groupMissionProgress.getGroupMember().getMemberId() != SecurityUtil.getCurrentMemberId())
                        throw new RuntimeException("접근 권한 없음");
                    groupMissionProgress.setComplete(complete);
                    groupProgressRepository.save(groupMissionProgress);
                    return GroupProgressDto.builder()
                            .missionName(groupMissionProgress.getGroupMission().getContent())
                            .groupProgressId(groupMissionProgress.getId())
                            .completed(groupMissionProgress.isComplete())
                            .memberInfo(MemberInfoDto.of(groupMissionProgress.getGroupMember().getMember()))
                            .build();
                }).orElseThrow(RuntimeException::new);
    }

    // delete
    public boolean delete(Long groupProgressId) {
        return groupProgressRepository.findById(groupProgressId)
                .map(groupMissionProgress -> {
                    groupProgressRepository.delete(groupMissionProgress);
                    return true;
                }).orElse(false);
    }
}
