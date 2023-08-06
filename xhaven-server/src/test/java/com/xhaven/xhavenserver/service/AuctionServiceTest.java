package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.exception.AuctionAlreadyFavoriteException;
import com.xhaven.xhavenserver.model.entity.Auction;
import com.xhaven.xhavenserver.model.entity.Category;
import com.xhaven.xhavenserver.model.entity.Image;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.repository.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceTest {

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    UserService userService;

    @Mock
    ImageService imageService;

    @Mock
    NotificationService notificationService;

    @InjectMocks
    AuctionService auctionService;

    @Test
    void Should_ReturnAuctionCorrectly_When_TryingToFetchWithValidId() {
        //given
        Optional<Auction> actualAuction = Optional.of(Auction.builder()
                .id(1L)
                .build());
        when(auctionRepository.findById(any())).thenReturn(actualAuction);

        Long VALID_ID = 1L;

        //when
        Auction auction = auctionService.getAuctionById(VALID_ID);

        //then
        assertEquals(actualAuction.get().getId(), auction.getId());
    }

    @Test
    void Should_ThrowException_When_TryingToFetchWithInvalidId() {
        //given
        when(auctionRepository.findById(any())).thenReturn(Optional.empty());
        Long INVALID_ID = 1L;

        //when
        Executable executable = () -> auctionService.getAuctionById(INVALID_ID);

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void Should_ReturnAuctionWithFavoriteFlag_When_TryingToFetchWithValidData() {
        //given
        Long AUCTION_ID = 1L;
        Auction savedFavoriteAuction = Auction.builder()
                .id(AUCTION_ID)
                .build();

        Long USER_ID = 1L;
        User user = User.builder()
                .id(USER_ID)
                .favoriteAuctions(List.of(savedFavoriteAuction))
                .build();

        when(userService.getUserById(USER_ID)).thenReturn(user);

        Optional<Auction> auction = Optional.of(savedFavoriteAuction);

        when(auctionRepository.findById(AUCTION_ID)).thenReturn(auction);
        when(imageService.getAuctionWithImageFiles(auction.get())).thenReturn(auction.get());

        //when
        Auction actualAuction = auctionService.getCompleteAuction(USER_ID, AUCTION_ID);

        //then
        assertTrue(actualAuction.getIsFavorite());
    }

    @Test
    void Should_ReturnAuctionWithImages_When_TryingToFetchWithValidData() {
        //given
        Long USER_ID = 1L;
        User user = User.builder()
                .favoriteAuctions(new ArrayList<>())
                .id(USER_ID)
                .build();

        when(userService.getUserById(USER_ID)).thenReturn(user);

        Long AUCTION_ID = 1L;

        Auction auctionWithoutImages = Auction.builder()
                .id(AUCTION_ID)
                .build();

        Auction auctionWithImages = Auction.builder()
                .id(AUCTION_ID)
                .images(List.of(
                        Image.builder()
                                .build(),
                        Image.builder()
                                .build())
                )
                .build();

        when(auctionRepository.findById(AUCTION_ID)).thenReturn(Optional.of(auctionWithoutImages));
        when(imageService.getAuctionWithImageFiles(auctionWithImages)).thenReturn(auctionWithImages);

        int EXPECTED_IMAGE_LIST_SIZE = 2;

        //when
        Auction actualAuction = auctionService.getCompleteAuction(USER_ID, AUCTION_ID);

        //then
        assertEquals(EXPECTED_IMAGE_LIST_SIZE, actualAuction.getImages().size());
    }

    @Test
    void Should_ReturnListOfAuctionsWithFavoriteFlag_When_TryingToFetchWithValidData() {

    }

    @Test
    void Should_ReturnListOfAuctionWithImages_When_TryingToFetchWithValidData() {

    }

    @Test
    void Should_SaveNewAuctionCorrectly_When_GivenValidData() {
        //given
        Long CURRENT_USER_ID = 1L;
        User currentUser = User.builder()
                .id(CURRENT_USER_ID)
                .build();

        when(userService.getUserById(CURRENT_USER_ID)).thenReturn(currentUser);

        BigDecimal SOME_PRICE = BigDecimal.valueOf(45.10);
        Auction auctionToSave = Auction.builder()
                .title("Some title")
                .description("Some description")
                .category(new Category())
                .contactInformation("John Doe")
                .phoneNumber("123456789")
                .price(SOME_PRICE)
                .build();

        MultipartFile[] auctionFiles = {getMultipartFileDummy()};


        when(imageService.saveImagesToFilesystem(auctionFiles)).thenReturn(List.of(Image.builder()
                .imageName("someFilename")
                .build()));

        //when
        auctionService.saveNewAuction(CURRENT_USER_ID, auctionToSave, auctionFiles);

        //TODO check postedAt timestamp - fixed clock
        //then
        assertEquals("someFilename", auctionToSave.getImages().get(0).getImageName());
        assertEquals(1, auctionToSave.getImages().size());
        assertEquals(CURRENT_USER_ID, auctionToSave.getOwner().getId());
        assertTrue(auctionToSave.getIsActive());
    }

    @Test
    void Should_ChangeAuctionStatus_When_GivenValidData() {
        //given
        Long AUCTION_ID = 1L;
        Auction auction = Auction.builder()
                .id(AUCTION_ID)
                .build();

        when(auctionRepository.findById(AUCTION_ID)).thenReturn(Optional.of(auction));

        //when
        auctionService.changeAuctionStatus(AUCTION_ID, true, true);

        //then
        assertTrue(auction.getIsActive());
    }

    @Test
    void Should_RemoveFavoriteAuctionOfFollowers_When_AuctionIsBeingTakenDown() {
        //given
        Long AUCTION_ID = 1L;
        Auction auction = Auction.builder()
                .id(AUCTION_ID)
                .build();

        List<Auction> favoriteAuctionsOfUser1 = new ArrayList<>();
        favoriteAuctionsOfUser1.add(auction);
        List<Auction> favoriteAuctionsOfUser2 = new ArrayList<>();
        favoriteAuctionsOfUser2.add(auction);

        List<User> followers = List.of(
                User.builder()
                        .favoriteAuctions(favoriteAuctionsOfUser1)
                        .build(),
                User.builder()
                        .favoriteAuctions(favoriteAuctionsOfUser2)
                        .build());

        when(userService.getFollowersOfAuction(auction)).thenReturn(followers);
        when(auctionRepository.findById(AUCTION_ID)).thenReturn(Optional.of(auction));

        //when
        auctionService.changeAuctionStatus(AUCTION_ID, false, true);

        //then
        assertTrue(followers.get(0).getFavoriteAuctions().isEmpty());
        assertTrue(followers.get(1).getFavoriteAuctions().isEmpty());
    }

    @Test
    void Should_AddAuctionToFavorites_When_GivenValidData() {
        //given
        Long NEW_AUCTION_ID = 1L;
        Auction newAuction = Auction.builder()
                .id(NEW_AUCTION_ID)
                .build();

        Long USER_ID = 1L;
        User user = User.builder()
                .id(USER_ID)
                .favoriteAuctions(new ArrayList<>())
                .build();

        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(auctionRepository.findById(NEW_AUCTION_ID)).thenReturn(Optional.of(newAuction));

        //when
        auctionService.addAuctionToFavorites(USER_ID, NEW_AUCTION_ID);

        //then
        assertEquals(1, user.getFavoriteAuctions().size());
    }

    @Test
    void Should_ThrowException_When_TryingToAddAuctionToFavoritesTwice() {
        //given
        Long AUCTION_ID = 1L;
        Auction savedFavoriteAuction = Auction.builder()
                .id(AUCTION_ID)
                .build();

        Long USER_ID = 1L;
        List<Auction> favoriteAuctions = new ArrayList<>();
        favoriteAuctions.add(savedFavoriteAuction);
        User user = User.builder()
                .id(USER_ID)
                .favoriteAuctions(favoriteAuctions)
                .build();

        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(auctionRepository.findById(AUCTION_ID)).thenReturn(Optional.of(savedFavoriteAuction));

        String EXPECTED_ERROR_MESSAGE = "Cannot add auction to favorites - auction is already added to favorites";

        //when
        Executable executable = () -> auctionService.addAuctionToFavorites(USER_ID, AUCTION_ID);

        //then
        assertThrows(AuctionAlreadyFavoriteException.class, executable, EXPECTED_ERROR_MESSAGE);
    }

    @Test
    void Should_RemoveAuctionFromFavorites_When_GivenValidData() {
        //given
        Long AUCTION_ID = 1L;
        Auction savedFavoriteAuction = Auction.builder()
                .id(AUCTION_ID)
                .build();

        Long USER_ID = 1L;
        List<Auction> favoriteAuctions = new ArrayList<>();
        favoriteAuctions.add(savedFavoriteAuction);
        User user = User.builder()
                .id(USER_ID)
                .favoriteAuctions(favoriteAuctions)
                .build();

        when(userService.getUserById(USER_ID)).thenReturn(user);
        when(auctionRepository.findById(AUCTION_ID)).thenReturn(Optional.of(savedFavoriteAuction));

        //when
        auctionService.removeAuctionFromFavorites(USER_ID, AUCTION_ID);

        //then
        assertTrue(user.getFavoriteAuctions().isEmpty());
    }

    private MultipartFile getMultipartFileDummy() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return "someFilename";
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }

}
