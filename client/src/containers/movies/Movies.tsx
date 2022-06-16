import React from 'react';
import { useHistory } from 'react-router';
import axios from '../../utils/axios';
import { useAsync } from '../../hooks/useAsync';
import { ItemList, Item } from '../../components';
import { FaArrowLeft } from 'react-icons/fa';

interface Props {}

interface MoviePreview {
  id: number;
  title: string;
  release: string;
  duration: string;
  image: string;
}

interface MoviePreviews extends Array<MoviePreview> {}

const Movies: React.FC<Props> = () => {
  const history = useHistory();

  const movieSelectedHandler = (id: number) => {
    history.push('/movies/' + id);
  };

  const getMovies = (): Promise<MoviePreviews> => {
    return new Promise((resolve, reject) => {
      axios
        .get('/movies/', {
          headers: {
            token: localStorage.token,
          },
        })
        .then(({ data }) => resolve(data))
        .catch((err) => reject(err));
    });
  };

  const { status, value, error } = useAsync<MoviePreviews>(getMovies);

  const renderSwitch = (param: string) => {
    switch (param) {
      case 'idle' || 'pending':
        return <p>Loading post...</p>;
      case 'error':
        return <h1> {error} </h1>;
      case 'success':
        return value !== null ? (
          <ItemList>
            <ItemList.Return to="/movies">
              <FaArrowLeft />
            </ItemList.Return>
            <ItemList.Header>Movies</ItemList.Header>
            <ItemList.Wrapper>
              {value.map((movie) => (
                <Item
                  movie
                  key={movie.id}
                  onClick={() => movieSelectedHandler(movie.id)}
                >
                  <Item.Image src={movie.image} alt="movie-poster" />
                  <Item.Title>{movie.title}</Item.Title>
                  <Item.Subtitle>
                    Release date: {movie.release}
                  </Item.Subtitle>
                  <Item.Subtitle>
                    Duration: {movie.duration} min
                  </Item.Subtitle>
                </Item>
              ))}
            </ItemList.Wrapper>
          </ItemList>
        ) : (
          <p>Something went wrong!</p>
        );
    }
  };
  return <>{renderSwitch(status)}</>;
};
export default Movies;
