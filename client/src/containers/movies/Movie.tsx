import React from 'react';
import { useParams, useHistory } from 'react-router-dom';
import axios from '../../utils/axios';
import { useAsync } from '../../hooks/useAsync';
import { FullItem } from '../../components';
import { FaArrowLeft } from 'react-icons/fa';
import * as ROUTES from '../../constants/routes';

interface Props {}

interface Params {
  id: string;
}

interface IMovie {
  id: number;
  description: string;
  duration: string;
  image: string;
  release: string;
  title: string;
}

const Movie: React.FC<Props> = () => {
  let history = useHistory();
  const params: Params = useParams();

  const getMovie = (): Promise<IMovie> => {
    return new Promise((resolve, reject) => {
      axios
        .get(ROUTES.MOVIES + '/' + params.id, {
          headers: {
            token: localStorage.token,
          },
        })
        .then(({ data }) => resolve(data))
        .catch((err) => reject(err));
    });
  };

  const { status, value, error } = useAsync<IMovie>(getMovie);

  const renderSwitch = (param: string) => {
    switch (param) {
      case 'idle' || 'pending':
        return <p>Loading post...</p>;
      case 'error':
        return <h1> {error} </h1>;
      case 'success':
        return value !== null ? (
          <FullItem>
            <FullItem.Wrapper>
              <FullItem.Return
                onClick={() => {
                  history.goBack();
                }}
              >
                <FaArrowLeft />
              </FullItem.Return>
              <FullItem.Text>
                <FullItem.Header>{value?.title}</FullItem.Header>
                <FullItem.AdditionalInfo>
                  <FullItem.Info>Release: {value?.release}</FullItem.Info>
                  <FullItem.Info>
                    Duration: {value?.duration} min
                  </FullItem.Info>
                  <FullItem.Info>
                    Director: TBA TBA
                    {/* Director: {value?.director_name} {value?.director_surname} */}
                  </FullItem.Info>
                </FullItem.AdditionalInfo>
                <FullItem.Description>
                  {value?.description}
                </FullItem.Description>
              </FullItem.Text>
              <FullItem.Image src={value?.image} alt="movie-poster" />
            </FullItem.Wrapper>
          </FullItem>
        ) : (
          <p>Something went wrong!</p>
        );
    }
  };

  return <>{renderSwitch(status)}</>;
};

export default Movie;
