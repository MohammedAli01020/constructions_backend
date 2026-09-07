package com.example.construction_api.repository;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.GlobalMachineStatisticsDto;
import com.example.construction_api.model.requests.MachineDamagesResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findByCode(String code);
    List<Machine> findAllByMachineStatus(MachineStatus machineStatus);

    @Modifying
    @Query("UPDATE Machine SET machineStatus = :machineStatus WHERE machineId = :machineId")
    void changeMachineState(@Param("machineStatus") MachineStatus machineStatus, @Param("machineId") Long machineId);

    @Query("SELECT  machine.machineTypes as name, machine.code as id, count (machine.code) as count,  (select sum(damageItem.cost) from DamageItem  damageItem where damageItem.damage.damageId = damage.damageId) as sum FROM Machine machine LEFT JOIN Damage damage " +
            "ON machine.machineId = damage.machine.machineId " +
            " WHERE damage.damageStatus = :damageStatus  and damage.endDateTime BETWEEN :startDate AND :endDate  GROUP BY machine.code")
    List<MachineDamagesResponse> fetchMachineDamagesLeftJoin(
            @Param("damageStatus") DamageStatus damageStatus,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);

    @Query("SELECT new com.example.construction_api.model.requests.GlobalMachineStatisticsDto(machine.machineTypes, machine.code, count (cost.costId), sum (cost.value)) FROM Machine machine LEFT JOIN Cost cost " +
            "ON machine.machineId = cost.machine.machineId " +
            " WHERE cost.dateTime BETWEEN :startDate AND :endDate  GROUP BY machine.code")
    List<GlobalMachineStatisticsDto> fetchMachineCostsLeftJoin(@Param("startDate") Long startDate,
                                                               @Param("endDate") Long endDate);

    @Query("SELECT new com.example.construction_api.model.requests.GlobalMachineStatisticsDto(machine.machineTypes, machine.code, count (shift.shiftId), sum (shift.price)) FROM Machine machine LEFT JOIN Shift shift " +
            "ON machine.machineId = shift.machine.machineId " +
            "where shift.shiftStatus = :shiftStatus and shift.startDateTime BETWEEN :startDate AND :endDate GROUP BY machine.code")
    List<GlobalMachineStatisticsDto> fetchMachineShiftsLeftJoin(
            @Param("shiftStatus") ShiftStatus shiftStatus,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);
}


