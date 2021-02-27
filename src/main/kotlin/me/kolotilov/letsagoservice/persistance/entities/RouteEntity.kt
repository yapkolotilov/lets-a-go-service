package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Route
import javax.persistence.*

@Entity(name = "route")
data class RouteEntity(
        @Column(name = "name")
        val name: String?,
        @Column(name = "difficulty")
        val difficulty: Int?,
        @OneToOne(cascade = [CascadeType.ALL])
        val type: RouteTypeEntity?,
        @OneToOne(cascade = [CascadeType.ALL])
        val ground: RouteGroundEntity?,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "route_id")
        val points: List<PointEntity>,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "route_id")
        val entries: List<EntryEntity>,
        @OneToOne(cascade = [CascadeType.ALL])
        val owner: UserEntity,
        @Id
        @Column(name = "id")
        val id: Int = 0,
)

@Entity(name = "route_type")
data class RouteTypeEntity(
        @Id
        @Column(name = "name")
        val name: String
)

@Entity(name = "route_ground")
data class RouteGroundEntity(
        @Id
        @Column(name = "name")
        val name: String
)

fun Route.Type.toRouteTypeEntity() = RouteTypeEntity(
        name = name
)

fun RouteTypeEntity.toRouteType() = Route.Type.valueOf(name)

fun Route.Ground.toRouteGroundEntity() = RouteGroundEntity(
        name = name
)

fun RouteGroundEntity.toRouteGround() = Route.Ground.valueOf(name)

fun Route.toRouteEntity(): RouteEntity = RouteEntity(
        name = name,
        difficulty = difficulty,
        type = type?.toRouteTypeEntity(),
        ground = ground?.toRouteGroundEntity(),
        points = points.map { it.toPointEntity() },
        entries = entries.map { it.toEntryEntity() },
        owner = owner.toUserEntity(),
        id = id
)

fun RouteEntity.toRoute(): Route = Route(
        name = name,
        difficulty = difficulty,
        type = type?.toRouteType(),
        ground = ground?.toRouteGround(),
        points = points.map { it.toPoint() },
        entries = entries.map { it.toEntry() },
        owner = owner.toUser(),
        id = id
)