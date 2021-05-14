package com.dinotrove.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.dinotrove.entities.DinoArticle;
import com.dinotrove.entities.Dinosaur;
import com.dinotrove.entities.Video;
import com.dinotrove.repositories.DinoArticleRepository;
import com.dinotrove.repositories.DinosaurRepository;
import com.dinotrove.repositories.VideoRepository;

@Controller
@RequestMapping("/dataScraper")
public class DataScraperController {

	private final DinosaurRepository dinosaurRepository;
	private final VideoRepository videoRepository;
	private final DinoArticleRepository dinoArticleRepository;

	@Autowired
	public DataScraperController(DinosaurRepository dinosaurRepository, VideoRepository videoRepository,
			DinoArticleRepository dinoArticleRepository) {
		this.dinosaurRepository = dinosaurRepository;
		this.videoRepository = videoRepository;
		this.dinoArticleRepository = dinoArticleRepository;
	}

	@GetMapping("/dinosaurs/import/kaggle")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> loadKaggleData() throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/dinosaur-data/kaggle-dinosaur.csv")));
		String newDinoLine = null;
		List<Dinosaur> scrapedDinosaurs = new ArrayList<Dinosaur>();
		while ((newDinoLine = br.readLine()) != null) {
			String[] splitStrings = newDinoLine.split(",");
			Dinosaur dinosaur = new Dinosaur();
			dinosaur.setDescription(newDinoLine);
			dinosaur.setName(splitStrings[0]);
			dinosaur.setDinosaurType(splitStrings[2]);
			dinosaur.setAllFactsDocumentId("1");
			scrapedDinosaurs.add(dinosaur);
		}
		for (Dinosaur scrapedDinosaur : scrapedDinosaurs) {
			List<Dinosaur> dinosaursWithSameName = dinosaurRepository.findByName(scrapedDinosaur.getName());
			if (CollectionUtils.isEmpty(dinosaursWithSameName)) {
				dinosaurRepository.save(scrapedDinosaur);
			}
		}
		return new ResponseEntity<>("Kaggle dataset Scraped successfully", HttpStatus.OK);
	}

	@GetMapping("/dinosaurs/import/dinosaurpicturesorg")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> getIndex(
			@RequestParam(name = "fetchCount", required = false, defaultValue = "10000") Long fetchCount)
			throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> responseEntity = restTemplate
				.getForEntity("https://dinosaurpictures.org/api/category/all", Object[].class);

		Object[] dinoArray = responseEntity.getBody();

		Dinosaur dinosaur = new Dinosaur();
		int scrapeCount = 0;
		for (Object dino : dinoArray) {
			dinosaur.setName(dino.toString());
			Document dinoDocument = Jsoup.connect("https://dinosaurpictures.org/" + dino + "-pictures").get();
			Elements introDivs = dinoDocument.getElementsByClass("intro");
			Elements descriptionParas = introDivs.get(0).getElementsByTag("p");
			StringBuilder dinoDescription = new StringBuilder();
			Iterator<Element> iterator = descriptionParas.iterator();
			while (iterator.hasNext()) {
				Element para = iterator.next();
				dinoDescription.append(para.text());
				dinoDescription.append("\n");
			}
			dinosaur.setDescription(dinoDescription.toString());
			if (dinoDescription.toString().toLowerCase().contains("carnivore")) {
				dinosaur.setDinosaurType("Carnivore");
			} else if (dinoDescription.toString().toLowerCase().contains("herbivore")) {
				dinosaur.setDinosaurType("Herbivore");
			} else if (dinoDescription.toString().toLowerCase().contains("omnivore")) {
				dinosaur.setDinosaurType("Omnivore");
			} else {
				dinosaur.setDinosaurType("Unknown");
			}
			List<Dinosaur> dinosaursWithSameName = dinosaurRepository.findByName(dinosaur.getName());
			if (CollectionUtils.isEmpty(dinosaursWithSameName)) {
				dinosaur.setAllFactsDocumentId("1");
				List<Dinosaur> findByName = dinosaurRepository.findByName(dinosaur.getName());
				if (CollectionUtils.isEmpty(findByName)) {
					dinosaurRepository.save(dinosaur);
				}
			}
			scrapeCount++;
			if (scrapeCount > fetchCount)
				break;
		}

		return new ResponseEntity<>("dinosaurpictures.org dataset Scraped successfully", HttpStatus.OK);
	}

	@GetMapping("/dinosaurs/import/videos")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> loadYoutubeVideos() throws Exception {
		Iterable<Dinosaur> findAll = dinosaurRepository.findAll();
		for (Dinosaur dinosaur : findAll) {
			Video video = new Video();
			video.setName(dinosaur.getName() + "Video");
			video.setVideoLength((double) new Random().nextInt(500));
			video.setVideoTitle(dinosaur.getDescription().substring(0,
					20 < dinosaur.getDescription().length() ? 20 : dinosaur.getDescription().length() - 1));
			String[] video_urls = { "https://www.youtube.com/watch?v=VIGqad64wcs",
					"https://www.youtube.com/watch?v=WVT8yB7mDMU", "https://www.youtube.com/watch?v=gnv0s0WvKZM",
					"https://www.youtube.com/watch?v=DFqkGbdawLo", "https://www.youtube.com/watch?v=mwMN-892Jc4",
					"https://www.youtube.com/watch?v=bElVNx093vw", "https://www.youtube.com/watch?v=WHQM18Guv5c",
					"https://www.youtube.com/watch?v=wLZX1QhISZk" };
			video.setVideoUrl(video_urls[new Random().nextInt(video_urls.length)]);
			if (CollectionUtils.isEmpty(video.getDinosaurs())) {
				video.setDinosaurs(new HashSet<Dinosaur>());
			}
			video.getDinosaurs().add(dinosaur);
			videoRepository.save(video);
		}
		return new ResponseEntity<>("Updated video for each dinosaur", HttpStatus.OK);
	}

	@GetMapping("/dinosaurs/import/videos/generateData")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> generateVideoData() throws Exception {
		Iterable<Video> findAll = videoRepository.findAll();
		for (Video video : findAll) {
			int numberOfVideos = new Random().nextInt(10);
			for (int i = 0; i < numberOfVideos; i++) {
				video.getDinosaurs().add(getADino());
			}
			videoRepository.save(video);
		}
		return new ResponseEntity<>("Updated video for each dinosaur", HttpStatus.OK);
	}

	@GetMapping("/dinosaurs/import/articles")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> generateArticleData() throws Exception {
		String authors[] = { "Cassandra Neace", "JOHN PICKRELL", "KEIRON PIM", "LOWELL DINGUS", "STANLEY HEDEEN",
				"MICHAEL CRICHTON" };
		String publishers[] = { "Amazon", "Packit", "B&N", "Apple", "ChildrensBook" };
		String pages[] = {
				"Dinosaurs are a diverse group of reptiles[note 1] of the clade Dinosauria. They first appeared during the Triassic period, between 243 and 233.23 million years ago, although the exact origin and timing of the evolution of dinosaurs is the subject of active research. They became the dominant terrestrial vertebrates after the Triassic–Jurassic extinction event 201.3 million years ago; their dominance continued throughout the Jurassic and Cretaceous periods. The fossil record shows that birds are modern feathered dinosaurs, having evolved from earlier theropods during the Late Jurassic epoch, and are the only dinosaur lineage to survive the Cretaceous–Paleogene extinction event approximately 66 million years ago. Dinosaurs can therefore be divided into avian dinosaurs, or birds; and the extinct non-avian dinosaurs, which are all dinosaurs other than birds.",
				"Dinosaurs are a varied group of animals from taxonomic, morphological and ecological standpoints. Birds, at over 10,700 living species, are among the most diverse group of vertebrates. Using fossil evidence, paleontologists have identified over 900 distinct genera and more than 1,000 different species of non-avian dinosaurs. Dinosaurs are represented on every continent by both extant species (birds) and fossil remains. Through the first half of the 20th century, before birds were recognized as dinosaurs, most of the scientific community believed dinosaurs to have been sluggish and cold-blooded. Most research conducted since the 1970s, however, has indicated that dinosaurs were active animals with elevated metabolisms and numerous adaptations for social interaction. Some were herbivorous, others carnivorous. Evidence suggests that all dinosaurs were egg-laying; and that nest-building was a trait shared by many dinosaurs, both avian and non-avian.",
				"While dinosaurs were ancestrally bipedal, many extinct groups included quadrupedal species, and some were able to shift between these stances. Elaborate display structures such as horns or crests are common to all dinosaur groups, and some extinct groups developed skeletal modifications such as bony armor and spines. While the dinosaurs' modern-day surviving avian lineage (birds) are generally small due to the constraints of flight, many prehistoric dinosaurs (non-avian and avian) were large-bodied—the largest sauropod dinosaurs are estimated to have reached lengths of 39.7 meters (130 feet) and heights of 18 m (59 ft) and were the largest land animals of all time. The misconception that non-avian dinosaurs were uniformly gigantic is based in part on preservation bias, as large, sturdy bones are more likely to last until they are fossilized. Many dinosaurs were quite small, some measuring about 50 centimeters (20 inches) in length.",
				"The first dinosaur fossils were recognized in the early 19th century, with the name \"dinosaur\" (meaning \"terrible lizard\") having been coined by Sir Richard Owen in 1841 to refer to these \"great fossil lizards\". Since then, mounted fossil dinosaur skeletons have been major attractions at museums worldwide, and dinosaurs have become an enduring part of popular culture. The large sizes of some dinosaurs, as well as their seemingly monstrous and fantastic nature, have ensured their regular appearance in best-selling books and films, such as Jurassic Park. Persistent public enthusiasm for the animals has resulted in significant funding for dinosaur science, and new discoveries are regularly covered by the media." };
		long totalDinosaurs = dinosaurRepository.getTotalDinosaurs();
		for (int j = 0; j < totalDinosaurs; j++) {
			DinoArticle dinoArticle = new DinoArticle();
			dinoArticle.setArticleAuthor(authors[new Random().nextInt(authors.length)]);
			dinoArticle.setDinosaurId(getADino().getDinosaurId());
			dinoArticle.setPublishDate(new Date());
			dinoArticle.setPublisher(publishers[new Random().nextInt(publishers.length)]);

			dinoArticle.setArticlePages(Arrays.asList(pages));

			dinoArticle.setArticleSummary("A short summary of the article");

			dinoArticle.setTableOfContents("A long table of contents");

			dinoArticle.setReferences(Arrays.asList("https://archive.org/details/dinosaursofairev0000paul",
					"https://en.wikipedia.org/wiki/Abrams_Books", ""));

			dinoArticleRepository.save(dinoArticle);
		}
		return new ResponseEntity<>("Updated video for each dinosaur", HttpStatus.OK);

	}

	private Dinosaur getADino() {
		Long maxDinosaurId = dinosaurRepository.getMaxDinosaurId();
		Dinosaur aDino = null;
		while (true) {
			long dinosaurId = (long) new Random().nextInt(maxDinosaurId.intValue());
			Optional<Dinosaur> findById = dinosaurRepository.findById(dinosaurId);
			if (findById.isPresent()) {
				aDino = findById.get();
				break;
			}
		}
		return aDino;
	}
}
