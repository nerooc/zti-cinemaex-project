import React from 'react';
import { useHistory } from 'react-router';
import axios from '../../utils/axios';
import { useAsync } from '../../hooks/useAsync';
import { ItemList, Item } from '../../components';
import { FaArrowLeft } from 'react-icons/fa';

interface Props {}

interface ActorPreview {
  id: number;
  name: string;
  surname: string;
  image: string;
}

interface ActorPreviews extends Array<ActorPreview> {}

const Actors: React.FC<Props> = () => {
  const history = useHistory();

  const actorSelectedHandler = (id: number) => {
    history.push('/actors/' + id);
  };

  const getActors = (): Promise<ActorPreviews> => {
    return new Promise((resolve, reject) => {
      axios
        .get('/actors/', {
          headers: {
            token: localStorage.token,
          },
        })
        .then(({ data }) => resolve(data))
        .catch((err) => reject(err));
    });
  };

  const { status, value, error } = useAsync<ActorPreviews>(getActors);

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
            <ItemList.Header>Actors</ItemList.Header>
            <ItemList.Wrapper>
              {value.map((actor) => (
                <Item
                  actor
                  key={actor.id}
                  onClick={() => actorSelectedHandler(actor.id)}
                >
                  <Item.Image src={actor.image} alt="actor-poster" />
                  <Item.Title>
                    {actor.name} {actor.surname}
                  </Item.Title>
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
export default Actors;
