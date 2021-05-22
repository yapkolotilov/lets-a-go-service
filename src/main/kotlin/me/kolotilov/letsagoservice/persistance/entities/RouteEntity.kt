package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Route
import javax.persistence.*

@Entity(name = "route")
data class RouteEntity(
        @Column(name = "name")
        val name: String?,
        @Column(name = "difficulty")
        val difficulty: Int,
        @Enumerated(EnumType.STRING)
        val type: Route.Type?,
        @Enumerated(EnumType.STRING)
        val ground: Route.Ground?,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "route_id")
        val points: List<PointEntity>,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "route_id")
        val entries: List<EntryEntity>,
        @Column(name = "public")
        val public: Boolean,
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id: Int,
)

fun Route.toRouteEntity(): RouteEntity = RouteEntity(
        name = name,
        difficulty = difficulty,
        type = type,
        ground = ground,
        points = points.map { it.toPointEntity() },
        entries = entries.map { it.toEntryEntity() },
        public = isPublic,
        id = id
)

fun RouteEntity.toRoute(): Route = Route(
        name = name,
        difficulty = difficulty,
        type = type,
        ground = ground,
        points = points.map { it.toPoint() },
        entries = entries.map { it.toEntry() },
        isPublic = public,
        id = id
)