package me.kolotilov.letsagoservice

import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.domain.services.*
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import me.kolotilov.letsagoservice.persistance.repositories.*
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.event.annotation.BeforeTestMethod

@SpringBootTest
@TestPropertySource(locations=["classpath:test.properties"])
class BaseTests {

    @Autowired
    protected lateinit var authService: AuthService
    @Autowired
    protected lateinit var illnessService: IllnessService
    @Autowired
    protected lateinit var mapService: MapService
    @Autowired
    protected lateinit var symptomService: SymptomService
    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var entryRepository: EntryRepository
    @Autowired
    protected lateinit var filterRepository: FilterRepository
    @Autowired
    protected lateinit var illnessRepository: IllnessRepository
    @Autowired
    protected lateinit var pointRepository: PointRepository
    @Autowired
    protected lateinit var routeRepository: RouteRepository
    @Autowired
    protected lateinit var symptomRepository: SymptomRepository
    @Autowired
    protected lateinit var userRepository: UserRepository

    private val user = User(
        username = "test@gmail.com",
        password = "test1234",
        confirmationUrl = ""
    )

    init {
        val newUser = org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            emptyList()
        )
        val authentication: Authentication = Mockito.mock(Authentication::class.java)
        val securityContext: SecurityContext = Mockito.mock(SecurityContext::class.java)
        Mockito.`when`(securityContext.authentication).thenReturn(authentication)
        Mockito.`when`(authentication.principal).thenReturn(newUser)
        SecurityContextHolder.setContext(securityContext)
    }

    @BeforeEach
    fun mockData() {
        mockUsers()
    }

    private fun mockUsers() {
        userRepository.save(user.toUserEntity())
    }
}