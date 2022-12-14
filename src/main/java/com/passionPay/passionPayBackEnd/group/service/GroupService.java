package com.passionPay.passionPayBackEnd.group.service;

import com.passionPay.passionPayBackEnd.group.dto.*;
import com.passionPay.passionPayBackEnd.group.domain.Group;
import com.passionPay.passionPayBackEnd.group.domain.GroupMember;
import com.passionPay.passionPayBackEnd.group.domain.GroupMission;
import com.passionPay.passionPayBackEnd.group.domain.GroupPost;
import com.passionPay.passionPayBackEnd.group.payload.CreateGroupPayload;
import com.passionPay.passionPayBackEnd.group.repository.*;
import com.passionPay.passionPayBackEnd.member.repository.MemberRepository;
import com.passionPay.passionPayBackEnd.planner.repository.PlannerRepository;
import com.passionPay.passionPayBackEnd.task.repository.TaskRepository;
import com.passionPay.passionPayBackEnd.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupMissionRepository groupMissionRepository;
    private final GroupPostRepository groupPostRepository;
    private final GroupCommentRepository groupCommentRepository;
    private final GroupLikeRepository groupLikeRepository;
    private final GroupProgressRepository groupProgressRepository;
    private final GroupProgressService groupProgressService;
    private final TaskRepository taskRepository;
    private final PlannerRepository plannerRepository;

    public GroupService(GroupRepository groupRepository, MemberRepository memberRepository, GroupMemberRepository groupMemberRepository, GroupMissionRepository groupMissionRepository, GroupPostRepository groupPostRepository, GroupCommentRepository groupCommentRepository, GroupLikeRepository groupLikeRepository, GroupProgressRepository groupProgressRepository, GroupProgressService groupProgressService, TaskRepository taskRepository, PlannerRepository plannerRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupMissionRepository = groupMissionRepository;
        this.groupPostRepository = groupPostRepository;
        this.groupCommentRepository = groupCommentRepository;
        this.groupLikeRepository = groupLikeRepository;
        this.groupProgressRepository = groupProgressRepository;
        this.groupProgressService = groupProgressService;
        this.taskRepository = taskRepository;
        this.plannerRepository = plannerRepository;
    }

    // group ??????
    public GroupInfoDto saveGroup(CreateGroupPayload groupRequestDto) {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(member -> {
                    // group ??????
                    Group group = CreateGroupPayload.from(groupRequestDto);
                    GroupMember groupMember = GroupMember.builder()
                            .group(group)
                            .member(member)
                            .build();

                    // ????????? group mission ??????
                    List<GroupMission> groupMissions = groupRequestDto.getGroupMissions()
                            .stream()
                            .map(missionName -> {
                                GroupMission groupMission = GroupMission.builder()
                                        .group(group)
                                        .missionName(missionName)
                                        .build();
                                return groupMission;
                            }).collect(Collectors.toList());

                    group.addGroupMember(groupMember);
                    groupRepository.save(group);
                    groupMissionRepository.saveAll(groupMissions);

                    groupMemberRepository.save(groupMember);
                    return GroupInfoDto.from(group, groupMissionRepository.findByGroup(group));
                })
                .orElseThrow(RuntimeException::new);
    }

    // ?????? group ??????
    @Transactional
    public GroupDetailDto getGroupInfoList(Long memberId, LocalDate today) {
        // my groups
        List<MyGroupDto> myGroupDto = groupRepository.findMyGroupsByMemberId(memberId)
                .stream()
                .map(group -> {
                    ProgressDto progressDto = ProgressDto.builder()
                            .missionProgress(groupProgressService.getMyCount(group))
                            .timeProgress(0)
                            .build();

                    plannerRepository.findByMemberIdAndDate(memberId, today)
                            .ifPresent(planner -> {
                                int timeProgress = (planner.getCurrentStudyTime().toSecondOfDay() * 1000) / group.getGroupTimeGoal();
                                progressDto.setTimeProgress(timeProgress);
                            });
                    return MyGroupDto.from(group, progressDto);
                }).collect(Collectors.toList());

        // other groups
        // TODO: ?????? ?????? ????????????
        List<OtherGroupDto> otherGroupDto = groupRepository.findOtherGroupsByMemberId(SecurityUtil.getCurrentMemberId())
                .stream().map(group -> {
                    int groupAvgStudyTime = 0;
                    ProgressDto progressDto = ProgressDto.builder()
                            .missionProgress(groupProgressService.getGroupAvgCount(group))
                            .timeProgress(groupAvgStudyTime)
                            .build();
                    return OtherGroupDto.from(group, progressDto, groupAvgStudyTime);
                }).collect(Collectors.toList());

        // dto
        return GroupDetailDto.builder()
                .myGroupDto(myGroupDto)
                .otherGroupDto(otherGroupDto)
                .build();
    }

    // group id ??? group info ??????
    @Transactional
    public GroupInfoDto getGroupInfoById(Long groupId) {
        return groupRepository.findById(groupId)
                .map(group -> GroupInfoDto.from(group, groupMissionRepository.findByGroup(group)))
                .orElseThrow(RuntimeException::new);
    }

    // group ????????????
    @Transactional
    public GroupInfoDto joinGroupByGroupId(Long groupId, Long memberId) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    // group - member ???????????? ??????
                    GroupMember groupMember = memberRepository.findById(memberId)
                            .map(member ->
                                groupMemberRepository.save(GroupMember.builder()
                                        .group(group)
                                        .member(member)
                                        .build()))
                            .orElseThrow(RuntimeException::new);
                    // group ??? member ??????
                    group.addGroupMember(groupMember);
                    groupRepository.save(group);
                    // group info dto ??? ??????
                    return GroupInfoDto.from(group, groupMissionRepository.findByGroup(group));
                })
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public GroupInfoDto updateGroup(Long groupId, CreateGroupPayload groupRequestDto) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    group.setGroupPassword(groupRequestDto.getGroupPassword());
                    group.setMaxMember(groupRequestDto.getMaxMember());
                    group.setGroupTimeGoal(groupRequestDto.getGroupTimeGoal());

                    // group.setGroupName(groupRequestDto.getGroupName());
                    groupRepository.save(group);
                    return GroupInfoDto.from(group, groupMissionRepository.findByGroup(group));
                }).orElseThrow(RuntimeException::new);
    }

    @Transactional
    // TODO : group ???????????? ?????? ??? ????????? DB ??? ?????? ????????? ?????? ????????????
    // TODO : group ?????? ??? ???????????? ??????????????? check ( group goals, group post ...)
    public boolean deleteGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    // ???????????? ??????
                    groupPostRepository.deleteByGroupId(groupId);
                    groupMissionRepository.deleteByGroup(group);
                    // group ??????
                    groupRepository.delete(group);
                    return true;
                }).orElse(false);
    }

    @Transactional
    public boolean deleteGroupMember(Long groupId, Long memberId) {
        return groupMemberRepository.findByMemberIdAndGroupId(memberId, groupId)
                .map(groupMember -> {
                    Group group = groupMember.getGroup();

                    /*  foreign key constraint:
                     *  group comment / like -> group post -> group member  */
                    List<GroupPost> groupPosts = groupPostRepository.findByGroupMember(groupMember);

                    // 1. ????????? ????????? ??????
                    for (GroupPost post: groupPosts) {
                        // ????????? ???????????? ?????? ??????
                        groupLikeRepository.deleteByGroupPost(post);
                        groupCommentRepository.deleteByGroupPost(post);

                        // ????????? ??????
                        groupPostRepository.delete(post);
                    }

                    // 2. ????????? ?????? ??????
                    groupCommentRepository.deleteByGroupMember(groupMember);
                    // 3. ????????? ????????? ??????
                    groupLikeRepository.deleteByGroupMember(groupMember);

                    // 3. ?????? ??????
                    groupMemberRepository.delete(groupMember);
                    group.deleteGroupMember(groupMember);
                    groupRepository.save(group);

                    return true;
                }).orElse(false);
    }
}
